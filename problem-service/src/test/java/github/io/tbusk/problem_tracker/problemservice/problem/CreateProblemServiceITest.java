package github.io.tbusk.problem_tracker.problemservice.problem;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.CreateProblemDTO;
import github.io.tbusk.problem_tracker.problemservice.problem.services.CreateProblemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
public class CreateProblemServiceITest {

    @Autowired
    private CreateProblemService createProblemService;

    @Autowired
    private ProblemRepository problemRepository;

    @Test
    void create_throws_exceptions_when_args_are_null() {
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(null));
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                new CreateProblemDTO(
                        null,
                        "https://leetcode.com/problems/binary-search",
                        "Leetcode",
                        "Easy",
                        List.of("Binary Search"))
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                null,
                                "Leetcode",
                                "Easy",
                                List.of("Binary Search"))
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                null,
                                "Easy",
                                List.of("Binary Search"))
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                null,
                                List.of("Binary Search"))
                )
        );
    }

    @Test
    void create_throws_exceptions_when_args_cannot_be_found() {
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "",
                                "Easy",
                                List.of("Binary Search"))
                )
        );
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Test",
                                "Easy",
                                List.of("Binary Search"))
                )
        );

        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "",
                                List.of("Binary Search"))
                )
        );
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Test",
                                List.of("Binary Search"))
                )
        );
    }

    @Test
    void create_throws_exceptions_when_args_cannot_be_validated() {
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Easy",
                                List.of("Binary Search"))
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "a",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Easy",
                                List.of("Binary Search"))
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "X",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Easy",
                                List.of("Binary Search"))
                )
        );

        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "",
                                "Leetcode",
                                "Easy",
                                List.of("Binary Search"))
                )
        );
    }

    @Test
    void create_adds_problems() throws URISyntaxException, ProblemServiceException {
        CreateProblemDTO request = new CreateProblemDTO(
                "Pangram",
                "https://www.hackerrank.com/challenges/pangrams",
                "HackerRank",
                "Easy",
                List.of("String")
        );
        createProblemService.create(request);

        assertTrue(problemRepository.findByDetails(request.name(), request.difficulty(), request.platformName()).isPresent());

        request = new CreateProblemDTO(
                "Remove Duplicates from Sorted List II",
                "https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii",
                "Leetcode",
                "Medium",
                List.of("String")
        );
        createProblemService.create(request);

        assertTrue(problemRepository.findByDetails(request.name(), request.difficulty(), request.platformName()).isPresent());

        request = new CreateProblemDTO(
                "Two Sum",
                "https://algo.monster/courses/foundation/two_sum",
                "Algo Monster",
                "Easy",
                List.of("Array")
        );
        createProblemService.create(request);

        assertTrue(problemRepository.findByDetails(request.name(), request.difficulty(), request.platformName()).isPresent());
    }
}
