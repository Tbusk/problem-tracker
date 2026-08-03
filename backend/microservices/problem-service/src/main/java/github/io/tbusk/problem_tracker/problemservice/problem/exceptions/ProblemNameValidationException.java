package github.io.tbusk.problem_tracker.problemservice.problem.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a problem name fails validation, e.g., when it is empty or too long.
 */
public class ProblemNameValidationException extends ProblemServiceException {
    /**
     * Creates an exception with a message indicating the name is not valid
     *
     * @param name the invalid name provided
     */
    public ProblemNameValidationException(String name) {
        super(String.format("'%s' is not a valid name.", name));
    }
}
