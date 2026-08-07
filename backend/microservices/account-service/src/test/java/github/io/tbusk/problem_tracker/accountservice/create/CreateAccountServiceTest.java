package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateRequestDTO;
import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateSuccessDTO;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.EmailAddressInUseException;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.AccountServiceException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.InvalidEmailException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.StateException;
import github.io.tbusk.problem_tracker.accountservice.role.Role;
import github.io.tbusk.problem_tracker.accountservice.role.RoleRepository;
import github.io.tbusk.problem_tracker.accountservice.user.User;
import github.io.tbusk.problem_tracker.accountservice.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateAccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordCheckerService passwordCheckerService;

    @InjectMocks
    private CreateAccountService createAccountService;

    private static final String invalidPassword = "secuP!1";
    private static final String validPassword = "securePassword!1";
    private static final String validEmail = "test.user@example.com";

    @Test
    void shouldThrowIllegalArgumentExceptionWhenRequestIsNull() {
        assertThrows(IllegalArgumentException.class, () -> createAccountService.create(null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () -> createAccountService.create(new CreateRequestDTO(null, validPassword)));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenPasswordIsNull() {
        assertThrows(IllegalArgumentException.class, () -> createAccountService.create(new CreateRequestDTO(validEmail, null)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "test",
            "test.email",
            "@",
            "@gmail.com",
            "test@",
            "test@gmail",
            "test@gmail.",
            "test.email@email",
            "test.email@x.c" // extension needs to be 2+ chars
    })
    void shouldThrowInvalidEmailExceptionWhenGivenInvalidEmail(String emailAddress) {
        assertThrows(InvalidEmailException.class, () -> createAccountService.create(new CreateRequestDTO(emailAddress, validPassword)));
    }

    @Test
    void shouldThrowEmailAddressInUseExceptionWhenUserExists() {
        User existingUser = Mockito.mock(User.class);

        when(userRepository.findByEmailAddress(validEmail)).thenReturn(Optional.of(existingUser));

        assertThrows(EmailAddressInUseException.class, () -> createAccountService.create(new CreateRequestDTO(validEmail, validPassword)));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionWhenPasswordIsInvalid() throws InvalidPasswordException {
        when(userRepository.findByEmailAddress(validEmail)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(invalidPassword)).thenThrow(InvalidPasswordException.class);

        assertThrows(InvalidPasswordException.class, () -> createAccountService.create(new CreateRequestDTO(validEmail, invalidPassword)));
    }

    @Test
    void shouldThrowStateExceptionWhenDefaultRoleNotPresent() throws InvalidPasswordException {
        when(userRepository.findByEmailAddress(validEmail)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(validPassword)).thenReturn(true);

        when(roleRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.empty());

        assertThrows(StateException.class, () -> createAccountService.create(new CreateRequestDTO(validEmail, validPassword)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test@test.com",
            "test.user@test.com",
            "test@outlook.com",
            "test@gmail.com",
            "john.doe@example.co.uk"
    })
    void shouldCreateAccountWithValidEmails(String emailAddress) throws InvalidPasswordException {
        when(userRepository.findByEmailAddress(emailAddress)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(validPassword)).thenReturn(true);

        Role role = Mockito.mock(Role.class);

        when(roleRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.of(role));

        assertDoesNotThrow(() -> createAccountService.create(new CreateRequestDTO(emailAddress, validPassword)));
    }

    @Test
    void shouldCreateAccount() throws AccountServiceException {
        when(userRepository.findByEmailAddress(validEmail)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(validPassword)).thenReturn(true);

        when(passwordEncoder.encode(validPassword)).thenReturn("encoded");

        Role role = Mockito.mock(Role.class);

        when(roleRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.of(role));

        CreateSuccessDTO result = createAccountService.create(new CreateRequestDTO(validEmail, validPassword));

        assertEquals("Account successfully created! Please log in.", result.message());

        verify(passwordCheckerService).isValidPassword(validPassword);
        verify(passwordEncoder).encode(validPassword);
        verify(userRepository).save(any(User.class));
    }
}
