package github.io.tbusk.problem_tracker.accountservice.exceptions;

/**
 * Exception thrown when a state in the application is reached that shouldn't be
 */
public class StateException extends AccountServiceException {

    /**
     * @param message internal description of the problem
     */
    public StateException(String message) {
        super(message);
    }
}
