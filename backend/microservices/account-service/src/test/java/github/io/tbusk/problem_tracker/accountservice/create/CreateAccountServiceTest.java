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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateAccountServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
            "test.admin@gmail.com",
            "me@outlook.com",
            "john.doe@example.co.uk"
    })
    void shouldCreateAccount(String emailAddress) throws AccountServiceException {
        Role role = Mockito.mock(Role.class);

        when(userRepository.findByEmailAddress(emailAddress)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(validPassword)).thenReturn(true);

        when(roleRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.of(role));

        CreateSuccessDTO successDTO = createAccountService.create(new CreateRequestDTO(emailAddress, validPassword));

        assertEquals(CreateAccountService.SUCCESS_RESPONSE, successDTO.message());
    }

    @Test
    void shouldHaveCorrectEmailWhenAccountCreated() throws AccountServiceException {
        Role role = Mockito.mock(Role.class);

        when(userRepository.findByEmailAddress(validEmail)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(validPassword)).thenReturn(true);

        when(roleRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.of(role));

        createAccountService.create(new CreateRequestDTO(validEmail, validPassword));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User user = userCaptor.getValue();

        assertEquals(validEmail, user.getEmailAddress());
    }

    @Test
    void shouldHaveCorrectPasswordHashWhenAccountCreated() throws AccountServiceException {
        Role role = Mockito.mock(Role.class);

        when(userRepository.findByEmailAddress(validEmail)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(validPassword)).thenReturn(true);

        when(roleRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.of(role));

        createAccountService.create(new CreateRequestDTO(validEmail, validPassword));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User user = userCaptor.getValue();

        assertTrue(passwordEncoder.matches(validPassword, user.getPasswordHash()));
    }

    @Test
    void shouldHaveCorrectRoleWhenAccountCreated() throws AccountServiceException {
        Role role = Mockito.mock(Role.class);

        when(userRepository.findByEmailAddress(validEmail)).thenReturn(Optional.empty());

        when(passwordCheckerService.isValidPassword(validPassword)).thenReturn(true);

        when(roleRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.of(role));
        when(role.getName()).thenReturn(Role.DEFAULT_ROLE_NAME);

        createAccountService.create(new CreateRequestDTO(validEmail, validPassword));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User user = userCaptor.getValue();

        assertEquals(Role.DEFAULT_ROLE_NAME, user.getRole().getName());
    }
}
