package github.io.tbusk.problem_tracker.problemservice.problem.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when attempting to create a problem that already exists in the database.
 */
public class ProblemAlreadyExistsException extends ProblemServiceException {
    /**
     * Creates an exception with a message indicating the problem already exists
     */
    public ProblemAlreadyExistsException() {
        super("This problem already exists.");
    }
}
