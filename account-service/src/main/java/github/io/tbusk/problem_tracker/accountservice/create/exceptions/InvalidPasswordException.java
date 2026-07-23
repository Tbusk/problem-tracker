package github.io.tbusk.problem_tracker.accountservice.create.exceptions;

import github.io.tbusk.problem_tracker.accountservice.exceptions.AccountServiceException;

/**
 * Exception thrown when a supplied password fails validation checks.
 */
public class InvalidPasswordException extends AccountServiceException {
    /**
     * @param message description of why the password is invalid
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}
