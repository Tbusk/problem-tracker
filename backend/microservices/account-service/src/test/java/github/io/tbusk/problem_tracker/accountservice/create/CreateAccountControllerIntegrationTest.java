package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateRequestDTO;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.EmailAddressInUseException;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.AccountServiceException;
import github.io.tbusk.problem_tracker.accountservice.user.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
public class CreateAccountControllerIntegrationTest {

    @Autowired
    private CreateAccountController createAccountController;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateAccount() throws AccountServiceException {
        String emailAddress = "test@example.com";
        String password = "securePassword!1";

        createAccountController.createAccount(new CreateRequestDTO(emailAddress, password));

        assertTrue(userRepository.findByEmailAddress(emailAddress).isPresent());
    }

    @Test
    void shouldNotCreateAccountWhenExists() throws AccountServiceException {
        String emailAddress = "test@example.com";
        String password = "securePassword!1";

        createAccountController.createAccount(new CreateRequestDTO(emailAddress, password));

        assertThrows(EmailAddressInUseException.class, () -> createAccountController.createAccount(new CreateRequestDTO(emailAddress, password)));
    }

    @Test
    void shouldNotCreateAccountWhenInvalidPassword() {
        String emailAddress = "test@example.com";
        String password = "weak";

        assertThrows(InvalidPasswordException.class, () -> createAccountController.createAccount(new CreateRequestDTO(emailAddress, password)));
    }
}
