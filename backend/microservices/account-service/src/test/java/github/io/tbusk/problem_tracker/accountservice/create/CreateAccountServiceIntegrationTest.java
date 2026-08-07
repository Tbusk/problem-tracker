package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateRequestDTO;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.EmailAddressInUseException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.AccountServiceException;
import github.io.tbusk.problem_tracker.accountservice.role.Role;
import github.io.tbusk.problem_tracker.accountservice.user.User;
import github.io.tbusk.problem_tracker.accountservice.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class CreateAccountServiceIntegrationTest {

    @Autowired
    private CreateAccountService createAccountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String emailAddress = "test.user@example.com";
    private static final String password = "securePassword1!";

    @Test
    void shouldCreateAccountWithAssignedEmail() throws AccountServiceException {
        createAccountService.create(new CreateRequestDTO(emailAddress, password));

        assertTrue(userRepository.findByEmailAddress(emailAddress).isPresent());
    }

    @Test
    void shouldCreateAccountWithDefaultRole() throws AccountServiceException {
        createAccountService.create(new CreateRequestDTO(emailAddress, password));

        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        assertTrue(user.isPresent());

        assertEquals(Role.DEFAULT_ROLE_NAME, user.get().getRole().getName());
    }

    @Test
    void shouldCreateAccountWithPasswordEncoded() throws AccountServiceException {
        createAccountService.create(new CreateRequestDTO(emailAddress, password));

        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        assertTrue(user.isPresent());

        assertNotEquals(password, user.get().getPasswordHash());
        assertTrue(passwordEncoder.matches(password, user.get().getPasswordHash()));
    }

    @Test
    void shouldCreateAccountWithCreatedOnSet() throws AccountServiceException {
        createAccountService.create(new CreateRequestDTO(emailAddress, password));

        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        assertTrue(user.isPresent());

        assertNotNull(user.get().getCreatedOn());
    }

    @Test
    void shouldThrowEmailAddressInUseExceptionWhenInUse() throws AccountServiceException {
        createAccountService.create(new CreateRequestDTO(emailAddress, password));

        assertThrows(EmailAddressInUseException.class, () -> createAccountService.create(new CreateRequestDTO(emailAddress, password)));
    }
}
