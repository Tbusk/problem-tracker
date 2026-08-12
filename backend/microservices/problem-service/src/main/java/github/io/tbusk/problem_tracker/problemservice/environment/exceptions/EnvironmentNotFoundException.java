package github.io.tbusk.problem_tracker.problemservice.environment.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a requested environment is not found in the database, e.g., "Virtual".
 */
public class EnvironmentNotFoundException extends ProblemServiceException {

    /**
     * The error message indicating that a requested environment could not be found.
     */
    public static final String MESSAGE = "Cannot find the environment. Please check again.";

    /**
     * Creates an exception with a message indicating the environment was not found
     */
    public EnvironmentNotFoundException() {
        super(MESSAGE);
    }
}
