package github.io.tbusk.problem_tracker.problemservice.problem;

import github.io.tbusk.problem_tracker.problemservice.problem.sanitizers.ProblemSanitizer;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProblemSanitizerTest {


    @Test
    void getValidNameCharacters_is_setup_correctly() {
        char[] validNameCharacters = ProblemSanitizer.getValidNameCharacters();

        // 0 - 9
        for (int i = 0; i < 10; i++) {
            assertEquals(1, validNameCharacters['0' + i]);
        }

        // a - z, A - Z
        for (int i = 0; i < 26; i++) {
            assertEquals(1, validNameCharacters['a' + i]);
            assertEquals(1, validNameCharacters['A' + i]);
        }

        // specials & space
        assertEquals(1, validNameCharacters['(']);
        assertEquals(1, validNameCharacters[')']);
        assertEquals(1, validNameCharacters['-']);
        assertEquals(1, validNameCharacters['!']);
        assertEquals(1, validNameCharacters[' ']);

        // not valid
        assertEquals(0, validNameCharacters['{']);
        assertEquals(0, validNameCharacters['|']);
        assertEquals(0, validNameCharacters['}']);
        assertEquals(0, validNameCharacters['~']);
        assertEquals(0, validNameCharacters['`']);
        assertEquals(0, validNameCharacters['_']);
        assertEquals(0, validNameCharacters['^']);
        assertEquals(0, validNameCharacters[']']);
        assertEquals(0, validNameCharacters['[']);
        assertEquals(0, validNameCharacters['\\']);
        assertEquals(0, validNameCharacters['@']);
        assertEquals(0, validNameCharacters['?']);
        assertEquals(0, validNameCharacters['>']);
        assertEquals(0, validNameCharacters['=']);
        assertEquals(0, validNameCharacters['<']);
        assertEquals(0, validNameCharacters[';']);
        assertEquals(0, validNameCharacters[':']);
        assertEquals(0, validNameCharacters['"']);
        assertEquals(0, validNameCharacters['#']);
        assertEquals(0, validNameCharacters['$']);
        assertEquals(0, validNameCharacters['%']);
        assertEquals(0, validNameCharacters['&']);
        assertEquals(0, validNameCharacters['\'']);
        assertEquals(0, validNameCharacters['*']);
        assertEquals(0, validNameCharacters['+']);
        assertEquals(0, validNameCharacters[',']);
        assertEquals(0, validNameCharacters['.']);
        assertEquals(0, validNameCharacters['/']);
    }

    @Test
    void sanitizeName_sanitizes_correctly() {

        assertThrows(IllegalArgumentException.class, () -> ProblemSanitizer.sanitizeName(null));
        assertThrows(IllegalArgumentException.class, () -> ProblemSanitizer.sanitizeName(""));
        assertThrows(IllegalArgumentException.class, () -> ProblemSanitizer.sanitizeName(" "));

        assertEquals("Test", ProblemSanitizer.sanitizeName("Test"));
        assertEquals("Test", ProblemSanitizer.sanitizeName(" Test"));
        assertEquals("Test", ProblemSanitizer.sanitizeName("Test "));
        assertEquals("Test", ProblemSanitizer.sanitizeName(" Test "));
        assertEquals("Test Test Test", ProblemSanitizer.sanitizeName("Test Test Test"));
        assertEquals("12345", ProblemSanitizer.sanitizeName("12345"));
        assertEquals("Test", ProblemSanitizer.sanitizeName("[Test]"));
        assertEquals("Base 7", ProblemSanitizer.sanitizeName("Base 7"));
        assertEquals("Reverse String (in place)", ProblemSanitizer.sanitizeName("Reverse String (in place)"));
        assertEquals("Remove N-th Node from Linked List", ProblemSanitizer.sanitizeName("Remove N-th Node from Linked List"));
        assertEquals("Hackerrank in a String!", ProblemSanitizer.sanitizeName("Hackerrank in a String!"));

        // specials before, after, and in between
        assertEquals("Test Case Test", ProblemSanitizer.sanitizeName("{|}~`_^][\\@?>=<;:\"#$%&\'*+,./Test{|}~`_^][\\@?>=<;:\"#$%&\'*+,./ Case {|}~`_^][\\@?>=<;:\"#$%&\'*+,./Test{|}~`_^][\\@?>=<;:\"#$%&\'*+,./"));
    }

    @Test
    void sanitizeUrl_sanitizes_correctly() throws URISyntaxException {
        assertThrows(IllegalArgumentException.class, () -> ProblemSanitizer.sanitizeUrl(null));
        assertThrows(IllegalArgumentException.class, () -> ProblemSanitizer.sanitizeUrl(""));
        assertThrows(IllegalArgumentException.class, () -> ProblemSanitizer.sanitizeUrl(" "));

        // regular url works
        assertEquals("https://leetcode.com/problems/two-sum/", ProblemSanitizer.sanitizeUrl("https://leetcode.com/problems/two-sum/"));
        assertEquals("https://www.hackerrank.com/challenges/reduced-string", ProblemSanitizer.sanitizeUrl("https://www.hackerrank.com/challenges/reduced-string"));
        assertEquals("https://algo.monster/courses/foundation/relative_sort_array", ProblemSanitizer.sanitizeUrl("https://algo.monster/courses/foundation/relative_sort_array"));

        // strips queries and fragments
        assertEquals("https://www.hackerrank.com/challenges/arrays-ds/problem", ProblemSanitizer.sanitizeUrl("https://www.hackerrank.com/challenges/arrays-ds/problem?isFullScreen=true"));
        assertEquals("https://leetcode.com/problems/two-sum", ProblemSanitizer.sanitizeUrl("https://leetcode.com/problems/two-sum?x=y"));
        assertEquals("https://leetcode.com/problems/two-sum", ProblemSanitizer.sanitizeUrl("https://leetcode.com/problems/two-sum?x=y&y=z"));
    }
}
