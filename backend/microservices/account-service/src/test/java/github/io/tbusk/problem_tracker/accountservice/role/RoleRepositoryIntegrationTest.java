package github.io.tbusk.problem_tracker.accountservice.role;

import jakarta.transaction.Transactional;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static github.io.tbusk.problem_tracker.accountservice.role.Role.DEFAULT_ROLE_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
public class RoleRepositoryIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            DEFAULT_ROLE_NAME,
            "USER",
            "ADMIN"
    })
    void shouldFindRoleByName(String roleName) {
        assertTrue(roleRepository.findByName(roleName).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user",
            "User",
            "UsEr",
            "uSeR",
            "USER"
    })
    void shouldFindRoleByNameIsCaseInsensitive(String roleName) {
        assertTrue(roleRepository.findByName(roleName).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "TEST",
            "1",
            "@"
    })
    void shouldNotFindRoleByName(String roleName) {
        assertFalse(roleRepository.findByName(roleName).isPresent());
    }
}
