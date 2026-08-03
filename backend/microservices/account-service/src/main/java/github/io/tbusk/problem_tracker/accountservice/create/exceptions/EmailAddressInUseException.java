package github.io.tbusk.problem_tracker.accountservice.create.exceptions;


import github.io.tbusk.problem_tracker.accountservice.exceptions.AccountServiceException;

/**
 * Exception thrown when an attempt is made to register an account using an email address
 * that is already associated with an existing account.
 */
public class EmailAddressInUseException extends AccountServiceException {
    /**
     * Creates a new exception with a message indicating that the email address is already in use.
     */
    public EmailAddressInUseException() {
        super("It looks like this email address is already in use. Please log in instead.");
    }
}
