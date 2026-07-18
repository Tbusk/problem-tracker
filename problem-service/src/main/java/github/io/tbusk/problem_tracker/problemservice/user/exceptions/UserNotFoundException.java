package github.io.tbusk.problem_tracker.problemservice.user.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a user is not found in the system when searching for them.
 */
public class UserNotFoundException extends ProblemServiceException {

    /**
     * Creates an exception with a message indicating the user was not found by their email address
     */
    public UserNotFoundException() {
        super("Cannot find a user with that email address.");
    }
}
