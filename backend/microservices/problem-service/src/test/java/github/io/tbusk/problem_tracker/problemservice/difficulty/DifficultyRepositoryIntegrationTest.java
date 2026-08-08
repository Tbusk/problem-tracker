package github.io.tbusk.problem_tracker.problemservice.difficulty;

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
public class DifficultyRepositoryIntegrationTest {

    @Autowired
    private DifficultyRepository difficultyRepository;

    private static final List<String> seededDifficulties = List.of(
            "Easy",
            "Hard",
            "Medium"
    );

    @Test
    void shouldFindAll() {
        List<String> difficulties = new ArrayList<>(difficultyRepository.findAll());

        Collections.sort(difficulties);

        assertEquals(seededDifficulties, difficulties);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Easy",
            "Medium",
            "Hard"
    })
    void shouldFindByName(String name) {
        Optional<Difficulty> difficulty = difficultyRepository.findByName(name);

        assertTrue(difficulty.isPresent());

        assertEquals(name, difficulty.get().getName());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "Legendary",
            "TEST",
            "1",
            "$"
    })
    void shouldNotFindByName(String name) {
        assertFalse(difficultyRepository.findByName(name).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "EASY",
            "easy",
            "eAsY",
            "easY",
            "Easy"
    })
    void shouldFindByNameCaseInsensitive(String name) {
        Optional<Difficulty> difficulty = difficultyRepository.findByName(name);

        assertTrue(difficulty.isPresent());

        assertTrue(name.equalsIgnoreCase(difficulty.get().getName()));
    }
}
