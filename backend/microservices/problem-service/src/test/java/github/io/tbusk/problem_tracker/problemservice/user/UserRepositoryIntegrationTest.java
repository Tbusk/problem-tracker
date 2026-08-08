package github.io.tbusk.problem_tracker.problemservice.user;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            "test.user@test.com",
            "test.admin@test.com"
    })
    void shouldFindUserByEmailAddress(String emailAddress) {
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);

        assertTrue(user.isPresent());

        assertEquals(emailAddress, user.get().getEmailAddress());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "test",
            "test.moderator@outlook.com",
            "john@gmail.com"
    })
    void shouldNotFindUserByEmailAddress(String emailAddress) {
        assertFalse(userRepository.findByEmailAddress(emailAddress).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test.user@test.com",
            "TEST.USER@TEST.COM",
            "Test.User@Test.Com",
            "Test.user@test.com",
    })
    void shouldFindUserByEmailAddressCaseInsensitive(String emailAddress) {
        Optional<User> user = userRepository.findByEmailAddress(emailAddress);

        assertTrue(user.isPresent());

        assertTrue(emailAddress.equalsIgnoreCase(user.get().getEmailAddress()));
    }
}
