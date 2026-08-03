package github.io.tbusk.problem_tracker.problemservice.exception;

/**
 * Thrown when the page number is less than 0
 */
public class PageTooSmallException extends ProblemServiceException {

    /**
     * Creates an exception when the page number is less than 0
     */
    public PageTooSmallException() {
        super("Page must be at least 0");
    }
}
