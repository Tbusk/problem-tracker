package github.io.tbusk.problem_tracker.problemservice.problem.validators;

/**
 * Utility class for validating problem name and URL fields.
 *
 * It currently validates length and nullability constraints.
 */
public class ProblemValidator {

    /** Minimum allowed length for a problem name */
    public static final int NAME_MINIMUM_LENGTH = 2;
    /** Maximum allowed length for a problem name */
    public static final int NAME_MAXIMUM_LENGTH = 128;
    /** Minimum allowed length for a problem URL */
    public static final int URL_MINIMUM_LENGTH = 10;
    /** Maximum allowed length for a problem URL */
    public static final int URL_MAXIMUM_LENGTH = 512;

    /**
     * Validates a problem name is not null and falls within the allowed length range
     *
     * @param name the problem name to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean validateName(String name) {
        if (name == null) {
            return false;
        }

        if (name.trim().length() < NAME_MINIMUM_LENGTH) {
            return false;
        }

        if (name.trim().length() > NAME_MAXIMUM_LENGTH) {
            return false;
        }

        return true;
    }

    /**
     * Validates a problem URL is not null and falls within the allowed length range
     *
     * @param url the problem URL to validate
     * @return true if the URL is valid, false otherwise
     */
    public static boolean validateUrl(String url) {
        if (url == null) {
            return false;
        }

        if (url.trim().length() < URL_MINIMUM_LENGTH) {
            return false;
        }

        if (url.trim().length() > URL_MAXIMUM_LENGTH) {
            return false;
        }

        return true;
    }
}
