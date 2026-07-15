package github.io.tbusk.problem_tracker.problemservice.problem.sanitizers;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utility class for sanitizing problem names by removing invalid characters.
 *
 * Used primarily for sanitizing problem names before storing them in the database.
 */
public class ProblemSanitizer {

    /**
     * Lookup table mapping characters to whether they are valid in a problem name (nonzero means valid)
     *
     * Uses array to do in-cache (cpu) lookup with limited memory usage - quicker than hash-based lookup typically
     */
    static char[] validNameCharacters;

    /**
     * Sanitizes a problem name by stripping out any characters not in the valid set.
     * Only alphanumeric characters, parentheses, hyphens, exclamation marks, and spaces are allowed.
     *
     * @param name the raw problem name to sanitize
     * @return the sanitized name containing only valid characters
     */
    public static String sanitizeName(String name) {

        if (name == null) {
            throw new IllegalArgumentException("Problem name cannot be null");
        }

        if (name.isBlank()) {
            throw new IllegalArgumentException("Problem name cannot be empty");
        }

        // remove extra whitespaces
        name = name.trim();

        if (validNameCharacters == null) {
            validNameCharacters = getValidNameCharacters();
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < name.length(); i++) {
            if (validNameCharacters[name.charAt(i)] != 0) {
                builder.append(name.charAt(i));
            }
        }

        return builder.toString();
    }

    /**
     * Populates a character lookup table with lowercase letters, uppercase letters, numbers,
     * parentheses, hyphens, exclamation marks, and spaces.
     */
    public static char[] getValidNameCharacters() {
        // ascii printable chars up to 127
        char[] validChars = new char[127];

        // add lowercase
        for (int i = 0; i < 26; i++) {
            validChars[i + 'a']++;
        }

        // add uppercase
        for (int i = 0; i < 26; i++) {
            validChars[i + 'A']++;
        }

        // add numbers
        for (int i = 0; i < 10; i++) {
            validChars[i + '0']++;
        }

        // add specials and space
        validChars['(']++;
        validChars[')']++;
        validChars['-']++;
        validChars['!']++;
        validChars[' ']++;

        return validChars;
    }


    /**
     * Santizes a problem url by removing everything but the scheme, authority, and path
     *
     * @param url the problem URL to sanitize
     * @return the sanitized URL with just the scheme, authority, and path
     */
    public static String sanitizeUrl(String url) throws URISyntaxException {

        if (url == null) {
            throw new IllegalArgumentException("Problem url cannot be null");
        }

        if (url.isBlank()) {
            throw new IllegalArgumentException("Problem url cannot be empty");
        }

        URI uri = URI.create(url);

        return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, null).toString();
    }
}
