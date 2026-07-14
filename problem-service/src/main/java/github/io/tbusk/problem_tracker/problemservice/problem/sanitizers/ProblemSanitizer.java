package github.io.tbusk.problem_tracker.problemservice.problem.sanitizers;

public class ProblemSanitizer {

    static char[] validNameCharacters;

    // TODO: test sanitizeName
    // TODO: add javadoc for sanitizeName(), setupValidNameCharacters(), constants, and ProblemSanitizer
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
