package github.io.tbusk.problem_tracker.problemservice.userProblem;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.response.SuccessResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * REST controller that exposes user problem API endpoints.
 */
@RestController
@RequestMapping("/api/v1/user-problem")
public class UserProblemController {

    private UserProblemService userProblemService;

    /**
     * Creates a controller instance with the required service
     *
     * @param userProblemService service responsible for managing user problem records
     */
    public UserProblemController(UserProblemService userProblemService) {
        this.userProblemService = userProblemService;
    }

    /**
     * Adds a record that a user solved a competitive programming problem
     *
     * @param request   dto containing the problem name, platform, programming language, and minutes taken
     * @param principal the authenticated user
     * @return a success response indicating the problem was recorded
     * @throws ProblemServiceException if the user, problem, or programming language is not found
     * @throws IllegalArgumentException if a required argument is null or minutes is below the minimum
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponseDTO add(@RequestBody AddUserProblemDTO request, Principal principal) throws ProblemServiceException {
        return userProblemService.add(principal.getName(), request);
    }
}
