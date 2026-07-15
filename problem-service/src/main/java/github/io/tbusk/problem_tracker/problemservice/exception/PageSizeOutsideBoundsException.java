package github.io.tbusk.problem_tracker.problemservice.exception;

/**
 * Exception thrown when the page size is outside the allowed bounds
 */
public class PageSizeOutsideBoundsException extends ProblemServiceException {

    /**
     * Creates an exception when the page size is outside the allowed bounds
     *
     * @param maxSize the max page size
     * @param minSize the min page size
     */
    public PageSizeOutsideBoundsException(int maxSize, int minSize) {
        super(String.format("Page size must be between %d and %d", minSize, maxSize));
    }
}
