package github.io.tbusk.problem_tracker.problemservice.problem.sanitizers;

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

        if (validNameCharacters == null) {
            setupValidNameCharacters();
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
     * Populates the valid name characters lookup table with lowercase letters, uppercase letters,
     * parentheses, hyphens, exclamation marks, and spaces.
     */
    static void setupValidNameCharacters() {
        validNameCharacters = new char[127];

        // add lowercase
        for (int i = 0; i < 26; i++) {
            validNameCharacters[i + 'a']++;
        }

        // add uppercase
        for (int i = 0; i < 26; i++) {
            validNameCharacters[i + 'A']++;
        }

        // add specials and space
        validNameCharacters['(']++;
        validNameCharacters[')']++;
        validNameCharacters['-']++;
        validNameCharacters['!']++;
        validNameCharacters[' ']++;
    }
}
