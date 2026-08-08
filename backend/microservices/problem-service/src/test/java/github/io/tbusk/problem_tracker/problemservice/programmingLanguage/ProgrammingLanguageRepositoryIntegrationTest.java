package github.io.tbusk.problem_tracker.problemservice.programmingLanguage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class ProgrammingLanguageRepositoryIntegrationTest {
    
    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;
    
    private static final List<String> seededProgrammingLanguages = List.of(
            "C",
            "C#",
            "C++",
            "Dart",
            "Elixir",
            "Erlang",
            "Go",
            "Java",
            "JavaScript",
            "Kotlin",
            "PHP",
            "Python",
            "Python3",
            "Racket",
            "Ruby",
            "Rust",
            "Scala",
            "Swift",
            "TypeScript"
    );
    
    @Test
    void shouldFindAll() {
        List<String> programmingLanguages = new ArrayList<>(programmingLanguageRepository.findAll());
        
        Collections.sort(programmingLanguages);
        
        assertEquals(seededProgrammingLanguages, programmingLanguages);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Swift",
            "Ruby",
            "PHP",
            "Java",
            "C++",
            "JavaScript"
    })
    void shouldFindByName(String name) {
        Optional<ProgrammingLanguage> programmingLanguage = programmingLanguageRepository.findByName(name);

        assertTrue(programmingLanguage.isPresent());

        assertEquals(name, programmingLanguage.get().getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "A",
            "D",
            "Ada",
            "Objective-C",
            "Algol 68"
    })
    void shouldNotFindByName(String name) {
        assertFalse(programmingLanguageRepository.findByName(name).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "swift",
            "SWIFT",
            "Swift",
            "SwIfT",
            "c++",
            "C++",
            "Javascript",
            "JavaScript",
            "javascript",
            "JAVASCRIPT"
    })
    void shouldFindByNameCaseInsensitive(String name) {
        Optional<ProgrammingLanguage> programmingLanguage = programmingLanguageRepository.findByName(name);

        assertTrue(programmingLanguage.isPresent());

        assertTrue(name.equalsIgnoreCase(programmingLanguage.get().getName()));
    }
}
