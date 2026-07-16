package github.io.tbusk.problem_tracker.problemservice.problem.validators;

import java.net.URI;

/**
 * Utility class for validating problem name and URL fields.
 * <p>
 * It currently validates length, nullability, scheme, path, and domain constraints.
 */
public class ProblemValidator {

    /**
     * Minimum allowed length for a problem name
     */
    public static final int NAME_MINIMUM_LENGTH = 2;
    /**
     * Maximum allowed length for a problem name
     */
    public static final int NAME_MAXIMUM_LENGTH = 128;
    /**
     * Minimum allowed length for a problem URL
     */
    public static final int URL_MINIMUM_LENGTH = 10;
    /**
     * Maximum allowed length for a problem URL
     */
    public static final int URL_MAXIMUM_LENGTH = 512;

    /**
     * Minimum allowed length for a problem URL path
     */
    public static final int URL_PATH_MINIMUM_LENGTH = 4;

    /**
     * Validates a problem name is not null and falls within the allowed length range
     *
     * @param name the problem name to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (name.trim().length() < NAME_MINIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format("Name must be at least %d characters long", NAME_MINIMUM_LENGTH));
        }

        if (name.trim().length() > NAME_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format("Name must be at most %d characters long", NAME_MAXIMUM_LENGTH));
        }

        return true;
    }

    /**
     * Validates a problem URL is not null, falls within the allowed range, uses the correct scheme, and has the correct
     * host.
     *
     * @param url the problem URL to validate
     * @return true if the url is valid, otherwise exceptions will be thrown
     * @throws IllegalArgumentException if any part of the url is invalid, such as not using https, length outside
     *                                  range, platform domain mismatch, or no path.
     */
    public static boolean validateUrl(String url, String platformDomain) {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        if (platformDomain == null) {
            throw new IllegalArgumentException("Platform domain cannot be empty");
        }

        if (url.trim().length() < URL_MINIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format("URL must be at least %d characters long", URL_MINIMUM_LENGTH));
        }

        if (url.trim().length() > URL_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(String.format("URL must be at most %d characters long", URL_MAXIMUM_LENGTH));
        }

        URI uri = URI.create(url).normalize();

        if (uri.getScheme() == null) {
            throw new IllegalArgumentException("URL must have a scheme, e.g., https");
        }

        if (!uri.getScheme().equals("https")) {
            throw new IllegalArgumentException("URL must use the https scheme");
        }

        if (uri.getAuthority() == null) {
            throw new IllegalArgumentException("URL must have a domain");
        }

        // allow items with prefixes such as www rather than full comparison
        if (!uri.getAuthority().endsWith(platformDomain)) {
            throw new IllegalArgumentException(String.format("URL must have a domain matching its platform domain of %s", platformDomain));
        }

        if (uri.getPath() == null || uri.getPath().isEmpty() || uri.getPath().length() < URL_PATH_MINIMUM_LENGTH) {
            throw new IllegalArgumentException("URL must have a path");
        }

        return true;
    }
}
