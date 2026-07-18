package github.io.tbusk.problem_tracker.problemservice.platform.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a requested platform name is not found in the database, e.g., "Codeforces".
 */
public class PlatformNotFoundException extends ProblemServiceException {
    /**
     * Creates an exception with a message indicating the platform is not valid
     */
    public PlatformNotFoundException() {
        super("Cannot find the platform. Please check again.");
    }
}
