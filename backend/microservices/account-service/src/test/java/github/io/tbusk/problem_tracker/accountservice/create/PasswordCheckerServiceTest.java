package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PasswordCheckerServiceTest {

    private final PasswordCheckerService passwordCheckerService;

    public PasswordCheckerServiceTest() {
        this.passwordCheckerService = new PasswordCheckerService();
    }

    private final Character SPECIAL_CHAR = '!';

    private String passwordCreator(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("length cannot be less than 4");
        }


        if (!PasswordCheckerService.specialCharacters.contains(SPECIAL_CHAR)) {
            throw new RuntimeException(String.format("special character '%c' is no longer supported", SPECIAL_CHAR));
        }

        int remainingDigits = length - 3;
        return String.format("%c%c%c%s", 'a', 'A', SPECIAL_CHAR, "0".repeat(remainingDigits));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionForEmpty() {
        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(""));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionForTooFewChar() {
        String password = passwordCreator(PasswordCheckerService.MIN_PASSWORD_LENGTH - 1);

        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(password), String.format("'%s' is not a valid password", password));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionForTooManyChar() {
        String password = passwordCreator(PasswordCheckerService.MAX_PASSWORD_LENGTH + 1);

        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(password), String.format("'%s' is not a valid password", password));
    }


    @Test
    void shouldThrowInvalidPasswordExceptionForMissingLowercase() {
        String password = passwordCreator(passwordCheckerService.MIN_PASSWORD_LENGTH);
        String noLowercasePassword = password.replaceAll("a", "X");

        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(noLowercasePassword), String.format("'%s' is not a valid password", noLowercasePassword));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionForMissingUppercase() {
        String password = passwordCreator(passwordCheckerService.MIN_PASSWORD_LENGTH);
        String noUppercasePassword = password.replaceAll("A", "x");

        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(noUppercasePassword), String.format("'%s' is not a valid password", noUppercasePassword));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionForMissingDigit() {
        String password = passwordCreator(passwordCheckerService.MIN_PASSWORD_LENGTH);
        String noDigitPassword = password.replaceAll("[0-9]", "X");
        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(noDigitPassword), String.format("'%s' is not a valid password", noDigitPassword));
    }

    @Test
    void shouldThrowInvalidPasswordExceptionForMissingSymbol() {
        String password = passwordCreator(passwordCheckerService.MIN_PASSWORD_LENGTH);

        String noSymbolPassword = password.replace(SPECIAL_CHAR.toString(), "X");

        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(noSymbolPassword), String.format("'%s' is not a valid password", noSymbolPassword));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNull() {
        assertThrows(IllegalArgumentException.class, () -> passwordCheckerService.isValidPassword(null));
    }

    @Test
    void shouldReturnTrueForMinPasswordLength() throws InvalidPasswordException {
        String password = passwordCreator(passwordCheckerService.MIN_PASSWORD_LENGTH);

        assertTrue(passwordCheckerService.isValidPassword(password), String.format("'%s' is not a valid password", password));
    }

    @Test
    void shouldReturnTrueForMinPlusOnePasswordLength() throws InvalidPasswordException {
        String password = passwordCreator(passwordCheckerService.MIN_PASSWORD_LENGTH + 1);

        assertTrue(passwordCheckerService.isValidPassword(password), String.format("'%s' is not a valid password", password));
    }

    @Test
    void shouldReturnTrueForMaxPasswordLength() throws InvalidPasswordException {
        String password = passwordCreator(passwordCheckerService.MAX_PASSWORD_LENGTH);

        assertTrue(passwordCheckerService.isValidPassword(password), String.format("'%s' is not a valid password", password));
    }

    @Test
    void shouldReturnTrueForMaxPasswordMinusOne() throws InvalidPasswordException {
        String password = passwordCreator(passwordCheckerService.MAX_PASSWORD_LENGTH - 1);

        assertTrue(passwordCheckerService.isValidPassword(password), String.format("'%s' is not a valid password", password));
    }
}
