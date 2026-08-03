package github.io.tbusk.problem_tracker.problemservice.problem.controllers;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.CreateProblemDTO;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.GetProblemDTO;
import github.io.tbusk.problem_tracker.problemservice.problem.services.CreateProblemService;
import github.io.tbusk.problem_tracker.problemservice.problem.services.GetProblemService;
import github.io.tbusk.problem_tracker.problemservice.response.SuccessResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller that exposes problem API endpoints
 */
@RestController
@RequestMapping("/api/v1/problem")
public class ProblemControllerV1 {

    private CreateProblemService createProblemService;
    private GetProblemService getProblemService;

    /**
     * @param createProblemService services responsible for creating problems
     */
    public ProblemControllerV1(CreateProblemService createProblemService, GetProblemService getProblemService) {
        this.createProblemService = createProblemService;
        this.getProblemService = getProblemService;
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
    SuccessResponseDTO create(@RequestBody CreateProblemDTO createRequest) throws ProblemServiceException, URISyntaxException {
        return createProblemService.create(createRequest);
    }

    /**
     * Get all competitive programming problems, 10 per page unless otherwise specified
     * @param page page number, zero indexed
     * @param pageSize number of problems per page
     * @return list of problems
     * @throws ProblemServiceException if the page or page size is outside the bounds
     */
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<GetProblemDTO> getAllProblems(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize) throws ProblemServiceException {
        return getProblemService.getAll(page, pageSize);
    }
}
