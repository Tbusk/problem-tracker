package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PasswordCheckerService {

    Set<Character> specialCharacters = Set.of(
            '!', '@', '#', '$', '%', '^', '&', '*'
    );

    /**
     * The minimum length of a valid password.
     */
    public static final int MIN_PASSWORD_LENGTH = 8;
    /**
     * The maximum length of a valid password.
     */
    public static final int MAX_PASSWORD_LENGTH = 32;

    /**
     * Checks if a password is valid.
     * <p>
     * Valid passwords must be at least {@value MIN_PASSWORD_LENGTH} characters long and at most
     * {@value MAX_PASSWORD_LENGTH} characters long. They must also contain at least one special character, one digit,
     * one uppercase, and one lowercase character.
     *
     * @param password the password to check
     * @return true if the password is valid, false otherwise
     * @throws IllegalArgumentException if the password is null
     * @throws InvalidPasswordException if the password is too short, too long, or is missing a special character,
     *                                  digit, uppercase, or lowercase character
     */
    public boolean isValidPassword(String password) throws InvalidPasswordException {

        if (password == null) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidPasswordException(String.format("Password must be at least %d characters long", MIN_PASSWORD_LENGTH));
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidPasswordException(String.format("Password must be at most %d characters long", MAX_PASSWORD_LENGTH));
        }

        int lowercaseCount = 0;
        int uppercaseCount = 0;
        int digitCount = 0;
        int specialCount = 0;

        for (int i = 0; i < password.length(); i++) {

            char currentCharacter = password.charAt(i);

            if (Character.isLowerCase(currentCharacter)) {
                lowercaseCount++;
            }

            if (Character.isUpperCase(currentCharacter)) {
                uppercaseCount++;
            }

            if (Character.isDigit(currentCharacter)) {
                digitCount++;
            }

            if (specialCharacters.contains(currentCharacter)) {
                specialCount++;
            }
        }

        List<String> missingRequirements = new ArrayList<>();

        if (lowercaseCount == 0) {
            missingRequirements.add("lowercase");
        }

        if (uppercaseCount == 0) {
            missingRequirements.add("uppercase");
        }

        if (digitCount == 0) {
            missingRequirements.add("digit");
        }

        if (specialCount == 0) {
            missingRequirements.add("special character");
        }

        if (!missingRequirements.isEmpty()) {
            throw new InvalidPasswordException(String.format("Password is missing %s", String.join(", ", missingRequirements)));
        }

        return true;
    }
}
