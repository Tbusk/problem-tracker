package github.io.tbusk.problem_tracker.problemservice.platform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class PlatformRepositoryIntegrationTest {

    @Autowired
    private PlatformRepository platformRepository;

    private static final List<String> seededPlatformNames = List.of(
            "Algo Monster",
            "HackerRank",
            "Leetcode"
    );

    @Test
    void shouldFindAll() {
        List<String> platforms = new ArrayList<>(platformRepository.findAll());

        Collections.sort(platforms);

        assertEquals(seededPlatformNames, platforms);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Algo Monster",
            "HackerRank",
            "Leetcode"
    })
    void shouldFindByName(String name) {
        Optional<Platform> platform = platformRepository.findByName(name);

        assertTrue(platform.isPresent());

        assertEquals(name, platform.get().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "TEST",
            "Neetcode",
            "1",
            "$",
            "a"
    })
    void shouldNotFindByName(String name) {
        assertFalse(platformRepository.findByName(name).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "leetcode",
            "LEETCODE",
            "LeetCode",
            "leetCode",
            "Algo monster",
            "ALGO MONSTER",
            "algo monster",
            "ALGO Monster"
    })
    void shouldFindByNameCaseInsensitive(String name) {
        Optional<Platform> platform = platformRepository.findByName(name);

        assertTrue(platform.isPresent());

        assertTrue(name.equalsIgnoreCase(platform.get().getName()));
    }
}
