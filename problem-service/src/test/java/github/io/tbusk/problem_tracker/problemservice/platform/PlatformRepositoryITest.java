package github.io.tbusk.problem_tracker.problemservice.platform;

import github.io.tbusk.problem_tracker.problemservice.platform.PlatformRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class PlatformRepositoryITest {

    @Autowired
    private PlatformRepository platformRepository;

    @Test
    void findAll_returns_expected() {
        Collection<String> platforms = platformRepository.findAll();

        assertEquals(3, platforms.size());

        assertTrue(platforms.contains("Leetcode"));
        assertTrue(platforms.contains("HackerRank"));
        assertTrue(platforms.contains("Algo Monster"));
    }

    @Test
    void findByName_returns_expected() {
        assertTrue(platformRepository.findByName("Leetcode").isPresent());
        assertTrue(platformRepository.findByName("HackerRank").isPresent());
        assertTrue(platformRepository.findByName("Algo Monster").isPresent());

        assertFalse(platformRepository.findByName("").isPresent());
        assertFalse(platformRepository.findByName("Test").isPresent());
    }
}
