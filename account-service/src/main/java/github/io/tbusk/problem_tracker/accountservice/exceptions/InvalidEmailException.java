package github.io.tbusk.problem_tracker.accountservice.exceptions;

/**
 * Exception thrown when an email address is not a valid email address, for example, if it is missing a top level domain
 * or has invalid characters in it.
 */
public class InvalidEmailException extends AccountServiceException {


    /**
     * Creates a new exception with a message indicating the email is not valid.
     */
    public InvalidEmailException() {
        super("The email you provided is not valid. Please check your input and try again.");
    }
}
