package github.io.tbusk.problem_tracker.accountservice.user;

import github.io.tbusk.problem_tracker.accountservice.role.Role;
import github.io.tbusk.problem_tracker.accountservice.role.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserRepositoryITest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;


    @ParameterizedTest
    @ValueSource(strings = {
            "test@test.com",
            "user@example.com",
            "john.doe@gmail.com",
            "janesmith@yahoo.com",
            "admin@outlook.com",
            "test.user@hotmail.com",
            "user.name@example.org",
            "test@company.co.uk",
            "user@domain.de",
            "test.user@university.edu",
            "admin@agency.gov",
            "f.e@x.com",
            "user@subdomain.example.com",
            "firstname.lastname@company.net",
            "test@123.com",
            "user@a.io",
            "test.user@example.museum",
            "admin@example.co.jp"
    })
    void save_persists_a_user(String emailAddress) {
        String password = "Abc!1234";
        String roleName = "USER";
        Optional<Role> role = roleRepository.findByName(roleName);
        assertTrue(role.isPresent());

        User user = new User(emailAddress, passwordEncoder.encode(password), role.get());

        user = userRepository.save(user);

        assertTrue(userRepository.findByEmailAddress(emailAddress).isPresent());

        assertEquals(emailAddress, user.getEmailAddress());
        assertTrue(passwordEncoder.matches(password, user.getPasswordHash()));
        assertEquals(roleName, role.get().getName());
        assertFalse(user.getLocked());
        assertTrue(user.getEnabled());

        LocalDateTime now = LocalDateTime.now();

        // allowing some leeway for execution time to not make it fragile
        assertTrue(user.getCreatedOn().isBefore(now));
        assertTrue(user.getCreatedOn().isAfter(now.minusMinutes(1)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test@test.com",
            "user@example.com",
            "john.doe@gmail.com",
            "janesmith@yahoo.com",
            "admin@outlook.com",
            "test.user@hotmail.com",
            "user.name@example.org",
            "test@company.co.uk",
            "user@domain.de",
            "test.user@university.edu",
            "admin@agency.gov",
            "f.e@x.com",
            "user@subdomain.example.com",
            "firstname.lastname@company.net",
            "test@123.com",
            "user@a.io",
            "test.user@example.museum",
            "admin@example.co.jp"
    })
    void save_persists_an_admin(String emailAddress) {
        String password = "aVerySecurePassword!123";
        String roleName = "ADMIN";
        Optional<Role> role = roleRepository.findByName(roleName);
        assertTrue(role.isPresent());

        User user = new User(emailAddress, passwordEncoder.encode(password), role.get());

        user = userRepository.save(user);

        assertTrue(userRepository.findByEmailAddress(emailAddress).isPresent());

        assertEquals(emailAddress, user.getEmailAddress());
        assertTrue(passwordEncoder.matches(password, user.getPasswordHash()));
        assertEquals(roleName, role.get().getName());
        assertFalse(user.getLocked());
        assertTrue(user.getEnabled());

        LocalDateTime now = LocalDateTime.now();

        // allowing some leeway for execution time to not make it fragile
        assertTrue(user.getCreatedOn().isBefore(now));
        assertTrue(user.getCreatedOn().isAfter(now.minusMinutes(5)));
    }

    @Test
    void save_throws_exception_when_email_already_exists() {
        String existingEmailAddress = "test.user@test.com";
        String password = "Abc!1234";
        String roleName = "USER";
        Optional<Role> role = roleRepository.findByName(roleName);
        assertTrue(role.isPresent());

        User user = new User(existingEmailAddress, passwordEncoder.encode(password), role.get());

        // note: flush is needed for the transaction to commit and throw the exception
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
            entityManager.flush();
        });
    }

    @Test
    void save_throws_exception_when_admin_email_already_exists() {
        String existingEmailAddress = "test.admin@test.com";
        String password = "aVerySecurePassword!123";
        String roleName = "ADMIN";
        Optional<Role> role = roleRepository.findByName(roleName);
        assertTrue(role.isPresent());

        User user = new User(existingEmailAddress, passwordEncoder.encode(password), role.get());

        // note: flush is needed for the transaction to commit and throw the exception
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
            entityManager.flush();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "invalid@",
            "@invalid.com",
            "invalid.email",
            "invalid@.com",
            "invalid@@email.com",
            "invalid email@test.com",
            "invalid.email@",
            "",
            " ",
            "   ",
            "\t",
            "\n",
            "a",
            "@",
            "a@",
            "@a",
            ".@test.com",
            "test.@test.com",
            ".test@test.com",
            "test..user@test.com",
            "test@domain..",
            "test@-domain.com",
            "test@domain-.com",
            "invalid.email@domain.",
            "invalid.email@.domain.com"
    })
    void save_throws_exception_when_email_is_invalid(String invalidEmailAddress) {
        String password = "Abc!1234";
        String roleName = "USER";
        Optional<Role> role = roleRepository.findByName(roleName);
        assertTrue(role.isPresent());

        User user = new User(invalidEmailAddress, passwordEncoder.encode(password), role.get());

        // note: flush is needed for the transaction to commit and throw the exception
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            userRepository.save(user);
            entityManager.flush();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // domain names need to be at least two characters in length
            "test@test.c",
            // domain names need to be attached
            "test@testcom",
            "test.user@test.c",
            "test.user@testcom"
    })
    void save_throws_exception_when_email_missing_full_domain_extension(String emailWithoutFullDomainExtension) {
        String password = "Abc!1234";
        String roleName = "USER";
        Optional<Role> role = roleRepository.findByName(roleName);
        assertTrue(role.isPresent());

        User user = new User(emailWithoutFullDomainExtension, passwordEncoder.encode(password), role.get());

        // note: flush is needed for the transaction to commit and throw the exception
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
            entityManager.flush();
        });
    }


    @Test
    void save_updates_an_existing_user() {
        String existingEmailAddress = "test.user@test.com";
        Optional<User> optionalExistingUser = userRepository.findByEmailAddress(existingEmailAddress);
        assertTrue(optionalExistingUser.isPresent());
        User existingUser = optionalExistingUser.get();

        String updatedPassword = "UpdatedPassword!123";
        existingUser.setPasswordHash(passwordEncoder.encode(updatedPassword));
        existingUser.setLocked(true);

        userRepository.save(existingUser);

        Optional<User> optionalUpdatedUser = userRepository.findByEmailAddress(existingEmailAddress);
        assertTrue(optionalUpdatedUser.isPresent());
        User updatedUser = optionalUpdatedUser.get();

        assertEquals(existingEmailAddress, updatedUser.getEmailAddress());
        assertTrue(passwordEncoder.matches(updatedPassword, updatedUser.getPasswordHash()));
        assertTrue(updatedUser.getLocked());
    }

    @Test
    void save_updates_an_existing_admin() {
        String existingEmailAddress = "test.admin@test.com";
        Optional<User> optionalExistingUser = userRepository.findByEmailAddress(existingEmailAddress);
        assertTrue(optionalExistingUser.isPresent());
        User existingUser = optionalExistingUser.get();

        String updatedPassword = "UpdatedAdminPassword!456";
        existingUser.setPasswordHash(passwordEncoder.encode(updatedPassword));
        existingUser.setEnabled(false);

        userRepository.save(existingUser);

        Optional<User> optionalUpdatedUser = userRepository.findByEmailAddress(existingEmailAddress);
        assertTrue(optionalUpdatedUser.isPresent());
        User updatedUser = optionalUpdatedUser.get();

        assertEquals(existingEmailAddress, updatedUser.getEmailAddress());
        assertTrue(passwordEncoder.matches(updatedPassword, updatedUser.getPasswordHash()));
        assertFalse(updatedUser.getEnabled());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test.user@test.com",
            "test.admin@test.com"
    })
    void findByEmailAddress_finds_an_existing_user(String existingEmailAddress) {
        Optional<User> optionalUser = userRepository.findByEmailAddress(existingEmailAddress);

        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(existingEmailAddress, user.getEmailAddress());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "TEST.USER@TEST.COM",
            "Test.User@Test.Com",
            "tEST.uSER@tEST.cOM",
            "TesT.UseR@TesT.CoM",
            "test.USER@test.com",
            "TEST.user@TEST.com",
            "Test.user@test.com",
            "test.user@Test.com",
            "test.user@test.Com",
            "test.user@test.coM",
            "tEsT.aDmIn@tEsT.cOm",
            "Test.admin@test.com",
            "test.admin@Test.com",
            "test.admin@test.Com",
            "test.admin@test.coM"
    })
    void findByEmailAddress_is_not_case_sensitive(String emailAddress) {
        Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);

        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(user.getEmailAddress().toLowerCase(), emailAddress.toLowerCase());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "nonexistent.user@test.com",
            "another.fake@example.com",
            "not.real@gmail.com",
            "missing.user@yahoo.com",
            "absent@outlook.com",
            "no.such.user@hotmail.com",
            "does.not.exist@example.org",
            "not.found@company.co.uk",
            "fake.user@test.com",
            "",
    })
    @NullSource
    void findByEmailAddress_returns_empty_optional_when_email_not_found(String emailAddress) {
        Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);

        assertFalse(optionalUser.isPresent());
    }
}
