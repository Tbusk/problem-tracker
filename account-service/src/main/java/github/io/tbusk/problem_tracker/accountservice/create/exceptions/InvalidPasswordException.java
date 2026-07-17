package github.io.tbusk.problem_tracker.accountservice.create.exceptions;

/**
 * Exception thrown when a supplied password fails validation checks.
 */
public class InvalidPasswordException extends Exception {
    /**
     * @param message description of why the password is invalid
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}
