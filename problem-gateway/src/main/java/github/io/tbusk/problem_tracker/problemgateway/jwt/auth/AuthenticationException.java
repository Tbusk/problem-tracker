package github.io.tbusk.problem_tracker.problemgateway.jwt.auth;

/**
 * Exception thrown when user authentication fails, e.g., due to invalid credentials or a locked account.
 */
public class AuthenticationException extends Exception {
    /**
     * @param message description of why authentication failed
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
