package github.io.tbusk.problem_tracker.problemservice.problem.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a problem is not found.
 */
public class ProblemNotFoundException extends ProblemServiceException {

    /**
     * Creates an exception with a message indicating the problem was not found
     */
    public ProblemNotFoundException() {
        super("Cannot find a problem with that name. Please check again.");
    }
}
