package github.io.tbusk.problem_tracker.problemservice.problem;

import github.io.tbusk.problem_tracker.problemservice.difficulty.Difficulty;
import github.io.tbusk.problem_tracker.problemservice.difficulty.DifficultyRepository;
import github.io.tbusk.problem_tracker.problemservice.platform.Platform;
import github.io.tbusk.problem_tracker.problemservice.platform.PlatformRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class ProblemRepositoryITest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Test
    void save_actually_persists() {
        Page<Problem> problems = problemRepository.findAll(Pageable.ofSize(10));

        assertEquals(0, problems.getTotalElements());

        Problem problem = new Problem();
        problem.setName("Binary Search");

        Optional<Platform> possiblePlatform = platformRepository.findByName("Leetcode");
        assertTrue(possiblePlatform.isPresent());

        problem.setPlatform(possiblePlatform.get());

        Optional<Difficulty> possibleDifficulty = difficultyRepository.findByName("Easy");
        assertTrue(possibleDifficulty.isPresent());

        problem.setDifficulty(possibleDifficulty.get());
        problem.setUrl("https://leetcode.com/problems/binary-search");

        problem = problemRepository.save(problem);

        problems = problemRepository.findAll(Pageable.ofSize(10));

        assertEquals(1, problems.getTotalElements());

        assertEquals("Easy", problem.getDifficulty().getName());
        assertEquals("Leetcode", problem.getPlatform().getName());
        assertEquals("https://leetcode.com/problems/binary-search", problem.getUrl());

        // checking id is added to object after it persists
        assertNotNull(problem.getId());
    }

    @Test
    void findByDetails_finds_problems() {
        Problem problem = new Problem();
        problem.setName("Binary Search");

        Optional<Platform> possiblePlatform = platformRepository.findByName("Leetcode");
        assertTrue(possiblePlatform.isPresent());

        problem.setPlatform(possiblePlatform.get());

        Optional<Difficulty> possibleDifficulty = difficultyRepository.findByName("Easy");
        assertTrue(possibleDifficulty.isPresent());

        problem.setDifficulty(possibleDifficulty.get());
        problem.setUrl("https://leetcode.com/problems/binary-search");

        problem = problemRepository.save(problem);

        Optional<Problem> potentialProblem = problemRepository.findByDetails(problem.getName(), problem.getDifficulty().getName(), problem.getPlatform().getName());

        assertTrue(potentialProblem.isPresent());

        assertFalse(problemRepository.findByDetails("Binary Search and Sort", problem.getDifficulty().getName(), problem.getPlatform().getName()).isPresent());
        assertFalse(problemRepository.findByDetails("Delete a node", problem.getDifficulty().getName(), problem.getPlatform().getName()).isPresent());
        assertFalse(problemRepository.findByDetails("Binary Search", "Hard", problem.getPlatform().getName()).isPresent());
        assertFalse(problemRepository.findByDetails("Binary Search", "Medium", problem.getPlatform().getName()).isPresent());
        assertFalse(problemRepository.findByDetails("Binary Search", problem.getDifficulty().getName(), "Hackerrank").isPresent());
        assertFalse(problemRepository.findByDetails("Binary Search", problem.getDifficulty().getName(), "Algo Monster").isPresent());
    }
}
