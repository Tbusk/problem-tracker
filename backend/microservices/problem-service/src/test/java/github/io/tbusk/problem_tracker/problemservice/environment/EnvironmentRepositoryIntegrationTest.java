package github.io.tbusk.problem_tracker.problemservice.environment;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class EnvironmentRepositoryIntegrationTest {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            "Pen and Paper",
            "Whiteboard",
            "Basic IDE",
            "Full IDE",
            "AI Assisted",
            "Online Editor",
            "Terminal"
    })
    void shouldFindByName(String name) {
        Optional<Environment> environment = environmentRepository.findByName(name);

        assertTrue(environment.isPresent());

        assertEquals(name, environment.get().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "TEST",
            "Pen",
            "Paper",
            "1",
            "$",
            "a"
    })
    void shouldNotFindByName(String name) {
        assertFalse(environmentRepository.findByName(name).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "pen and paper",
            "PEN AND PAPER",
            "Pen And Paper",
            "WHITEBOARD",
            "whiteboard",
            "WhiteBoard",
            "basic ide",
            "BASIC IDE",
            "Basic Ide"
    })
    void shouldFindByNameCaseInsensitive(String name) {
        Optional<Environment> environment = environmentRepository.findByName(name);

        assertTrue(environment.isPresent());

        assertTrue(name.equalsIgnoreCase(environment.get().getName()));
    }
}
