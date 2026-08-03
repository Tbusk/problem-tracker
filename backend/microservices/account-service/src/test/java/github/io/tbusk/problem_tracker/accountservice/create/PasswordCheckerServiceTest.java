package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.AccountServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PasswordCheckerServiceTest {

    @Spy
    private PasswordCheckerService passwordCheckerService;

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "a",
            "A",
            "!",
            "1",
            "aA",
            "a@",
            "A%",
            "A1",
            "e3",
            "@%",
            "Ab1",
            "@ba",
            "!b3",
            "@@@",
            "aaa",
            "123",
            "EEE",
            "aB1@",
            "ffff",
            "LLLL",
            "1234",
            "@#$%",
            "aaB1@",
            "aaaaa",
            "MMMMM",
            "12345",
            "!@#$%",
            "aaaaaa",
            "QQQQQQ",
            "123456",
            "!@#$%^",
            "aB1@eF",
            "abcdefg",
            "ABCDEFG",
            "1234567",
            "!!!!!!!",
            "aB1@123"
    })
    void isValidPassword_throws_when_length_too_small(String passsword) {
        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(passsword));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abcde!12",
            "ABCDE!12",
            "12345678",
            "aaaaaaaa",
            "BBBBBBBB",
            "!!!!!!!!",
            "Abcdefgh",
            "Abcdefg!",
            "Abcdefg1",
            "!!!!!!!2",
            "!!!!!!!a",
            "!!!!!!!A",
            "AAAAAAA1",
            "AAAAAAA!",
            "AAAAAAAa",
            "A!AAAAA1",
            "a!aaaaa1",
            "1!11111a",
            "1!11111A",
            "11111111aB",
            "!!!!!!!!1a",

    })
    void isValidPassword_throws_for_missing_characters(String password) {
        assertThrows(AccountServiceException.class, () -> passwordCheckerService.isValidPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "mySecurePassword(1",
            "mySecurePassword)1",
            "mySecurePassword-1",
            "mySecurePassword~1",
            "mySecurePassword`1",
            "mySecurePassword\\1",
            "mySecurePassword'1",
            "mySecurePassword/1",
            "mySecurePassword+1",
            "mySecurePassword=1",
            "mySecurePassword<1",
            "mySecurePassword>1",
            "mySecurePassword,1",
            "mySecurePassword.1",
            "mySecurePassword;1",
            "mySecurePassword:1",
            "mySecurePassword\"1",
            "mySecurePassword?1",
            "mySecurePassword|1",
            "mySecurePassword_1",
            "mySecurePassword 1",
            "mySecurePassword{1",
            "mySecurePassword}1",
            "mySecurePassword[1",
            "mySecurePassword]1",

    })
    void isValidPassword_throws_for_invalid_characters(String password) {
        assertThrows(AccountServiceException.class, () -> passwordCheckerService.isValidPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "myVerySecureAndVeryLongSuperLongPassword!123",
            //missing chars
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            "111111111111111111111111111111111",
            "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
    })
    void isValidPassword_throws_when_length_too_long(String password) {
        assertThrows(InvalidPasswordException.class, () -> passwordCheckerService.isValidPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // Common valid passwords (mix of all required character classes)
            "myPass!1",
            "Password1!",
            "SecureP@ss1",
            "Hello!World1",
            "MyP@ssw0rd",
            "Test!ng123",
            "Admin#2024",
            "Welcome!1A",
            "Good!Day1A",
            "Nice@Time2a",
            "Great#Job3A",
            "Super$Code4a",
            "Cool%Dev5A",
            "Awesome^App6a",
            "Brilliant&Web7A",
            "Fantastic*API8a",
            "Excellent#DB9A",
            "Wonderful@UI0a",

            // Each allowed special character at minimum length
            "Ab1!cD2e",
            "Ab1@cD2e",
            "Ab1#cD2e",
            "Ab1$cD2e",
            "Ab1%cD2e",
            "Ab1^cD2e",
            "Ab1&cD2e",
            "Ab1*cD2e",

            // Various digit combinations
            "Pa0!sWor1d",
            "Pa2!sWor3d",
            "Pa4!sWor5d",
            "Pa6!sWor7d",
            "Pa8!sWor9d",

            // Multiple special characters combined
            "Aa1!bB2@",
            "Bb2#cC3$",
            "Cc3%dD4^",
            "Dd4&eE5*",
            "Ee5!fF6@",
            "Ff6#gG7$",
            "Gg7%hH8^",
            "Hh8&iI9*",
            "Ii9!jJ0@",
            "Jj0#kK1$",

            // Exactly minimum length (8 chars) with each special character
            "Aa1@bB2c",
            "Cc3$dD4e",
            "Ee5^fF6g",
            "Gg7&hH8i",
            "Ii9*jJ0k",
            "Kk1!lL2m",
            "Xy9!zA8b",

            // Longer passwords
            "Abc!1DefGhi@2",
            "Xy9!zA8bB@cD",
            "Pa0!sWor1dEf2@",
            "Sec3!ureP@55w0rD",
            "Str0ng!Pa55w0rd",

            // Longer passwords between 16 and 31 chars
            "MyVerySec1!Pa55w",
            "MyVerySec1!Pa55wOrdX",
            "MyVerySecurePass1!wOrDXx",
            "MyVerySecurePasswoRd1!wOrDXX",
            "MyVerySecurePassword1!wOrDXXxXx",

            // Exactly maximum length (32 chars)
            "Abc!1DefGhi@2JklMno$3PqrStu%4VW",
            "Xy9!zA8bB@cD1eE#fF2gG$hH3iI%jJ",
            "Aa1!bB2@cC3#dD4$eE5^fF6&gG7*hH8"
    })
    void isValidPassword_handles_valid_passwords(String password) {
        assertDoesNotThrow(() -> passwordCheckerService.isValidPassword(password));
    }

    @Test
    void isValidPassword_handles_nulls() {
        assertThrows(IllegalArgumentException.class, () -> passwordCheckerService.isValidPassword(null));
    }
}
