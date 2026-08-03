package github.io.tbusk.problem_tracker.problemservice.problem;

import github.io.tbusk.problem_tracker.problemservice.problem.validators.ProblemValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProblemValidatorTest {

    @Test
    void validateName_should_validate_correctly() {
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateName(null));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateName(""));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateName(" "));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateName("F"));

        assertTrue(ProblemValidator.validateName("Fizz Buzz"));
        assertTrue(ProblemValidator.validateName("Sort Characters by Frequency"));
        assertTrue(ProblemValidator.validateName("FB"));
        assertTrue(ProblemValidator.validateName("Hackerrank in a String!"));
        assertTrue(ProblemValidator.validateName("Find Index of the first occurrence in a string"));
        assertTrue(ProblemValidator.validateName("Reverse String (in place)"));

        StringBuilder tooLong = new StringBuilder();

        for (int i = 0; i < 129; i++) {
            tooLong.append("a");
        }

        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateName(tooLong.toString()));

        tooLong.delete(tooLong.length() - 1, tooLong.length());
        assertEquals(128, tooLong.length());

        assertTrue(ProblemValidator.validateName(tooLong.toString()));
    }

    @Test
    void validateUrl_should_validate_correctly() {
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(null, null));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(null, "leetcode.com"));
        // needs domain
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "https://leetcode.com/problems/reverse-vowels-of-a-string/",
                null
        ));
        // needs https
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "http://leetcode.com/problems/reverse-vowels-of-a-string/",
                "leetcode.com"
        ));
        // domain needs to match
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "https://leetcode.com/problems/reverse-vowels-of-a-string/",
                "leetcode.co"
        ));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "https://leetcode.com/problems/reverse-vowels-of-a-string/",
                "leetcode.co.uk"
        ));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "https://leetcode.com/problems/reverse-vowels-of-a-string/",
                "leetcode.net"
        ));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "https://leetcode.com/problems/reverse-vowels-of-a-string/",
                "hackerrank.com"
        ));
        // missing scheme
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "leetcode.com/problems/reverse-vowels-of-a-string/",
                "hackerrank.com"
        ));

        // missing/too short path
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "https://leetcode.com/",
                "leetcode.com"
        ));
        assertThrows(IllegalArgumentException.class, () -> ProblemValidator.validateUrl(
                "https://leetcode.com/ab",
                "leetcode.com"
        ));

        // valid
        assertTrue(ProblemValidator.validateUrl(
                "https://leetcode.com/problems/reverse-vowels-of-a-string/",
                "leetcode.com"
        ));
        assertTrue(ProblemValidator.validateUrl(
                "https://algo.monster/problems/binary_search_intro",
                "algo.monster"
        ));
        assertTrue(ProblemValidator.validateUrl(
                "https://leetcode.com/problems/roman-to-integer/",
                "leetcode.com"
        ));
        assertTrue(ProblemValidator.validateUrl(
                "https://leetcode.com/problems/find-the-winner-of-the-circular-game",
                "leetcode.com"
        ));
        assertTrue(ProblemValidator.validateUrl(
                "https://hackerrank.com/challenges/insert-a-node-at-a-specific-position-in-a-linked-list/",
                "hackerrank.com"
        ));
    }
}
