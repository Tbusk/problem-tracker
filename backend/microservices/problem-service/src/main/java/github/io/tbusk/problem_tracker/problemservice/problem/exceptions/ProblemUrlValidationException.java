package github.io.tbusk.problem_tracker.problemservice.problem.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a problem URL fails validation, e.g., when it is malformed or missing.
 */
public class ProblemUrlValidationException extends ProblemServiceException {
    /**
     * Creates an exception with a message indicating the URL is not valid
     *
     * @param url the invalid URL provided
     */
    public ProblemUrlValidationException(String url) {
        super(String.format("'%s' is not a valid url", url));
    }
}
