package github.io.tbusk.problem_tracker.accountservice.user;

import github.io.tbusk.problem_tracker.accountservice.role.Role;
import github.io.tbusk.problem_tracker.accountservice.role.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldSaveNewUser() {
        Optional<Role> role = roleRepository.findByName(Role.DEFAULT_ROLE_NAME);

        String emailAddress = "test@test.com";
        String passwordHash = "averylongandseriouslyhashedpassword";

        User user = new User(emailAddress, passwordHash, role.get());

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmailAddress(emailAddress);

        assertTrue(savedUser.isPresent());
        assertEquals(emailAddress, savedUser.get().getEmailAddress());
        assertEquals(passwordHash, savedUser.get().getPasswordHash());
        assertEquals(role.get(), savedUser.get().getRole());
        assertFalse(user.getLocked());
        assertTrue(user.getEnabled());
        assertNotNull(user.getCreatedOn());
    }

    @Test
    void shouldUpdateExistingUser() {
        Optional<Role> role = roleRepository.findByName(Role.DEFAULT_ROLE_NAME);

        String emailAddress = "test@test.com";
        String passwordHash = "averylongandseriouslyhashedpassword";

        User user = new User(emailAddress, passwordHash, role.get());

        userRepository.save(user);

        user = userRepository.findByEmailAddress(emailAddress).get();

        String newEmailAddress = "new@test.com";

        user.setEmailAddress(newEmailAddress);

        userRepository.save(user);

        user = userRepository.findByEmailAddress(newEmailAddress).get();

        assertEquals(newEmailAddress, user.getEmailAddress());
    }

    @Test
    void shouldFindUserByEmail() {
        Optional<Role> role = roleRepository.findByName(Role.DEFAULT_ROLE_NAME);

        String emailAddress = "test@test.com";
        String passwordHash = "averylongandseriouslyhashedpassword";

        User user = new User(emailAddress, passwordHash, role.get());

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmailAddress("test@test.com");

        assertTrue(savedUser.isPresent());
    }

    @Test
    void shouldFindUserByEmailCaseInsensitive() {
        Optional<Role> role = roleRepository.findByName(Role.DEFAULT_ROLE_NAME);

        String emailAddress = "test@test.com";
        String passwordHash = "averylongandseriouslyhashedpassword";

        User user = new User(emailAddress, passwordHash, role.get());

        userRepository.save(user);

        assertTrue(userRepository.findByEmailAddress("TEST@TEST.COM").isPresent());
        assertTrue(userRepository.findByEmailAddress("Test@Test.Com").isPresent());
        assertTrue(userRepository.findByEmailAddress("test@test.COM").isPresent());
        assertTrue(userRepository.findByEmailAddress("Test@test.com").isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "test",
            "test@test.com",
            "john.doe@gmail.com"
    })
    void shouldNotFindUserByEmail(String emailAddress) {
        assertFalse(userRepository.findByEmailAddress(emailAddress).isPresent());
    }

    @Test
    void shouldBeUniqueUser() {
        Optional<Role> role = roleRepository.findByName(Role.DEFAULT_ROLE_NAME);

        String emailAddress = "test@test.com";
        String passwordHash = "averylongandseriouslyhashedpassword";

        User user = new User(emailAddress, passwordHash, role.get());

        userRepository.save(user);

        entityManager.flush();

        User newUser = new User(emailAddress, passwordHash, role.get());

        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(newUser);
            entityManager.flush();
        });
    }
}
