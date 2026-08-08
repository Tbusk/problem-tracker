package github.io.tbusk.problem_tracker.problemservice.problem;

import github.io.tbusk.problem_tracker.problemservice.difficulty.Difficulty;
import github.io.tbusk.problem_tracker.problemservice.difficulty.DifficultyRepository;
import github.io.tbusk.problem_tracker.problemservice.platform.Platform;
import github.io.tbusk.problem_tracker.problemservice.platform.PlatformRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class ProblemRepositoryIntegrationTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Test
    void shouldSaveProblem() {
        String platformName = "Leetcode";
        String difficultyName = "Easy";
        String name = "Valid Parentheses";
        String url = "https://leetcode.com/problems/valid-parentheses/";

        Optional<Platform> platform = platformRepository.findByName(platformName);
        Optional<Difficulty> difficulty = difficultyRepository.findByName(difficultyName);

        assertTrue(platform.isPresent());
        assertTrue(difficulty.isPresent());

        Problem problem = new Problem(name, url, platform.get(), difficulty.get());

        Problem saved = problemRepository.save(problem);

        assertNotNull(saved.getId());
        assertEquals(name, saved.getName());
        assertEquals(url, saved.getUrl());
        assertEquals(platformName, saved.getPlatform().getName());
        assertEquals(difficultyName, saved.getDifficulty().getName());
    }

    @Test
    void shouldUpdateProblem() {
        String originalName = "Two Sum";
        String updatedName = "Two Sum II";
        String updatedUrl = "https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/";
        String platformName = "Leetcode";

        Optional<Problem> existing = problemRepository.findByNameAndPlatform(originalName, platformName);

        assertTrue(existing.isPresent());

        Problem problem = existing.get();
        problem.setName(updatedName);
        problem.setUrl(updatedUrl);

        problemRepository.save(problem);

        assertFalse(problemRepository.findByNameAndPlatform(originalName, platformName).isPresent());

        Optional<Problem> found = problemRepository.findByNameAndPlatform(updatedName, platformName);

        assertTrue(found.isPresent());
        assertEquals(updatedName, found.get().getName());
        assertEquals(updatedUrl, found.get().getUrl());
    }

    @ParameterizedTest
    @CsvSource({
            "Two Sum, Easy, Leetcode",
            "Climbing Stairs, Easy, Leetcode",
            "Longest Substring Without Repeating Characters, Medium, Leetcode",
            "Course Schedule, Medium, Leetcode",
            "Median of Two Sorted Arrays, Hard, Leetcode"
    })
    void shouldFindByDetails(String name, String difficulty, String platform) {
        Optional<Problem> problem = problemRepository.findByDetails(name, difficulty, platform);

        assertTrue(problem.isPresent());
        assertEquals(name, problem.get().getName());
        assertEquals(difficulty, problem.get().getDifficulty().getName());
        assertEquals(platform, problem.get().getPlatform().getName());
    }

    @ParameterizedTest
    @CsvSource({
            "Two Sum, Medium, Leetcode",
            "Two Sum, Easy, HackerRank",
            "Nonexistent Problem, Easy, Leetcode",
            "Climbing Stairs, Hard, Leetcode",
            "Course Schedule, Easy, Leetcode",
            "Two Sum, Easy, Algo Monster"
    })
    void shouldNotFindByDetails(String name, String difficulty, String platform) {
        assertFalse(problemRepository.findByDetails(name, difficulty, platform).isPresent());
    }

    @ParameterizedTest
    @CsvSource({
            "two sum, easy, leetcode",
            "TWO SUM, EASY, LEETCODE",
            "Two Sum, Easy, Leetcode",
            "tWO sUM, eASY, lEETCODE",
            "climbing stairs, easy, leetcode",
            "MEDIAN OF TWO SORTED ARRAYS, HARD, LEETCODE"
    })
    void shouldFindByDetailsCaseInsensitive(String name, String difficulty, String platform) {
        Optional<Problem> problem = problemRepository.findByDetails(name, difficulty, platform);

        assertTrue(problem.isPresent());
        assertTrue(name.equalsIgnoreCase(problem.get().getName()));
        assertTrue(difficulty.equalsIgnoreCase(problem.get().getDifficulty().getName()));
        assertTrue(platform.equalsIgnoreCase(problem.get().getPlatform().getName()));
    }

    @ParameterizedTest
    @CsvSource({
            "Two Sum, Leetcode",
            "Climbing Stairs, Leetcode",
            "Longest Substring Without Repeating Characters, Leetcode",
            "Course Schedule, Leetcode",
            "Median of Two Sorted Arrays, Leetcode"
    })
    void shouldFindByNameAndPlatform(String name, String platform) {
        Optional<Problem> problem = problemRepository.findByNameAndPlatform(name, platform);

        assertTrue(problem.isPresent());
        assertEquals(name, problem.get().getName());
        assertEquals(platform, problem.get().getPlatform().getName());
    }

    @ParameterizedTest
    @CsvSource({
            "Two Sum, HackerRank",
            "Nonexistent Problem, Leetcode",
            "Two Sum, Algo Monster",
            "Binary Search, Leetcode",
            "Course Schedule, HackerRank"
    })
    void shouldNotFindByNameAndPlatform(String name, String platform) {
        assertFalse(problemRepository.findByNameAndPlatform(name, platform).isPresent());
    }

    @ParameterizedTest
    @CsvSource({
            "two sum, leetcode",
            "TWO SUM, LEETCODE",
            "Two Sum, Leetcode",
            "tWo sUm, lEeTcOdE",
            "climbing stairs, leetcode",
            "COURSE SCHEDULE, LEETCODE"
    })
    void shouldFindByNameAndPlatformCaseInsensitive(String name, String platform) {
        Optional<Problem> problem = problemRepository.findByNameAndPlatform(name, platform);

        assertTrue(problem.isPresent());
        assertTrue(name.equalsIgnoreCase(problem.get().getName()));
        assertTrue(platform.equalsIgnoreCase(problem.get().getPlatform().getName()));
    }
}
