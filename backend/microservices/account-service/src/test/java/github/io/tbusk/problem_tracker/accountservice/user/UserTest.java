package github.io.tbusk.problem_tracker.accountservice.user;

import github.io.tbusk.problem_tracker.accountservice.role.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    private static final String validEmail = "test.user@example.com";
    private static final String passwordHash = "encoded";

    @Test
    void shouldCreateUserWithEmailSet() {
        Role role = Mockito.mock(Role.class);

        User user = new User(validEmail, passwordHash, role);

        assertEquals(validEmail, user.getEmailAddress());
    }

    @Test
    void shouldCreateUserWithPasswordSet() {
        Role role = Mockito.mock(Role.class);

        User user = new User(validEmail, passwordHash, role);

        assertEquals(passwordHash, user.getPasswordHash());
    }

    @Test
    void shouldCreateUserThatIsEnabled() {
        Role role = Mockito.mock(Role.class);

        User user = new User(validEmail, passwordHash, role);

        assertTrue(user.getEnabled());
    }

    @Test
    void shouldCreateUserThatIsUnlocked() {
        Role role = Mockito.mock(Role.class);

        User user = new User(validEmail, passwordHash, role);

        assertFalse(user.getLocked());
    }
}
