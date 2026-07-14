package github.io.tbusk.problem_tracker.problemservice.problem;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.CreateProblemDTO;
import github.io.tbusk.problem_tracker.problemservice.problem.services.CreateProblemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback
public class CreateProblemServiceITest {

    @Autowired
    private CreateProblemService createProblemService;

    @Test
    void create_throws_exceptions_when_args_are_null() {
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(null));
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                new CreateProblemDTO(
                        null,
                        "https://leetcode.com/problems/binary-search",
                        "Leetcode",
                        "Easy")
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                null,
                                "Leetcode",
                                "Easy")
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                null,
                                "Easy")
                )
        );
        assertThrows(IllegalArgumentException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                null)
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
                                "Easy")
                )
        );
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Test",
                                "Easy")
                )
        );

        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "")
                )
        );
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Test")
                )
        );
    }

    @Test
    void create_throws_exceptions_when_args_cannot_be_validated() {
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Easy")
                )
        );
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "a",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Easy")
                )
        );
        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "X",
                                "https://leetcode.com/problems/binary-search",
                                "Leetcode",
                                "Easy")
                )
        );

        assertThrows(ProblemServiceException.class, () -> createProblemService.create(
                        new CreateProblemDTO(
                                "Binary Search",
                                "",
                                "Leetcode",
                                "Easy")
                )
        );
    }
}
