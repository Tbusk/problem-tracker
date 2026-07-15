package github.io.tbusk.problem_tracker.problemservice.problem.controllers;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.CreateProblemDTO;
import github.io.tbusk.problem_tracker.problemservice.problem.services.CreateProblemService;
import github.io.tbusk.problem_tracker.problemservice.response.SuccessResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes problem API endpoints
 */
@RestController
@RequestMapping("/api/v1/problem")
public class ProblemControllerV1 {

    private CreateProblemService createProblemService;

    /**
     * @param createProblemService services responsible for creating problems
     */
    public ProblemControllerV1(CreateProblemService createProblemService) {
        this.createProblemService = createProblemService;
    }

    /**
     * Create a competitive programming problem
     *
     * @param createRequest dto containing content like name, difficulty, url, and platform
     * @return a success response dto with a message
     * @throws ProblemServiceException if validation fails for something, e.g., difficulty provided is not valid
     * @throws IllegalArgumentException if a required argument is null
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SuccessResponseDTO create(@RequestBody CreateProblemDTO createRequest) throws ProblemServiceException {
        return createProblemService.create(createRequest);
    }
}
