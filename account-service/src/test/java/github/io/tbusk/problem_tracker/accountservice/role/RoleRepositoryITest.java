package github.io.tbusk.problem_tracker.accountservice.role;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
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
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RoleRepositoryITest {

    @Autowired
    private RoleRepository roleRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            "USER",
            "ADMIN"
    })
    void findByName_finds_an_existing_role(String roleName) {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        assertTrue(optionalRole.isPresent());
        Role role = optionalRole.get();
        assertEquals(roleName, role.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user",
            "User",
            "uSER",
            "UsEr",
            "usER",
            "admin",
            "Admin",
            "aDMIN",
            "AdMiN",
            "adMIN"
    })
    void findByName_is_not_case_sensitive(String roleName) {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        assertTrue(optionalRole.isPresent());
        Role role = optionalRole.get();
        assertEquals(role.getName().toLowerCase(), roleName.toLowerCase());
    }

    @Test
    void findByName_returns_empty_optional_when_name_is_empty() {
        Optional<Role> optionalRole = roleRepository.findByName("");

        assertFalse(optionalRole.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "nonexistent",
            "GUEST",
            "MODERATOR",
            "SUPER_ADMIN",
            "user_role",
            "admin_user",
            "USERADMIN",
            " user",
            "user ",
            "USER1",
            "0",
            " ",
            "   ",
            "\t",
            "\n",
            "ROLE_USER"
    })
    @NullSource
    void findByName_returns_empty_optional_when_name_not_found(String roleName) {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        assertFalse(optionalRole.isPresent());
    }
}