package github.io.tbusk.problem_tracker.problemservice.difficulty;

import github.io.tbusk.problem_tracker.problemservice.difficulty.DifficultyRepository;
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
public class DifficultyRepositoryITest {

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Test
    void findAll_returns_expected() {
        Collection<String> difficulties = difficultyRepository.findAll();

        assertEquals(3, difficulties.size());

        assertTrue(difficulties.contains("Easy"));
        assertTrue(difficulties.contains("Medium"));
        assertTrue(difficulties.contains("Hard"));
    }

    @Test
    void findByName_returns_expected() {
        assertTrue(difficultyRepository.findByName("Easy").isPresent());
        assertTrue(difficultyRepository.findByName("Medium").isPresent());
        assertTrue(difficultyRepository.findByName("Hard").isPresent());

        assertFalse(difficultyRepository.findByName("").isPresent());
        assertFalse(difficultyRepository.findByName("Test").isPresent());
    }
}
