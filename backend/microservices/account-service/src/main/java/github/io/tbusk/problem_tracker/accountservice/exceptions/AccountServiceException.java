package github.io.tbusk.problem_tracker.accountservice.exceptions;

/**
 * Superclass for errors that can occur within the account service.
 */
public class AccountServiceException extends Exception {

    /**
     * Creates a new exception with the given message
     *
     * @param message description of the problem
     */
    public AccountServiceException(String message) {
        super(message);
    }
}
