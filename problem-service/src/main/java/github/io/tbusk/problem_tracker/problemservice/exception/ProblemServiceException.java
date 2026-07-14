package github.io.tbusk.problem_tracker.problemservice.exception;

/**
 * Superclass for all custom exceptions thrown by the problem service, such as validation failures or missing entities.
 */
public class ProblemServiceException extends Exception {
    /**
     * Creates an exception with the given detail message
     *
     * @param message the detail message describing the error
     */
    public ProblemServiceException(String message) {
        super(message);
    }
}
