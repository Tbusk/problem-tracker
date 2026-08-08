package github.io.tbusk.problem_tracker.problemservice.category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private static final List<String> seededNames = Arrays.asList(
            "Array",
            "Binary Search",
            "Bit Manipulation",
            "Breadth-First Search",
            "Database",
            "Depth-First Search",
            "Dynamic Programming",
            "Greedy",
            "Hash Table",
            "Matrix",
            "Prefix Sum",
            "Sorting",
            "String",
            "Tree",
            "Two Pointers"
    );

    @Test
    void shouldFindAllCategories() {
        List<String> names = categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        Collections.sort(names);

        assertEquals(seededNames, names);
    }

    @Test
    void shouldFindAllCategoryNames() {
        List<String> names = new ArrayList<>(categoryRepository.findAllNames());

        Collections.sort(names);

        assertEquals(seededNames, names);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Tree",
            "Matrix",
            "Array",
            "String",
            "Database",
            "Hash Table"
    })
    void shouldFindByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);

        assertTrue(category.isPresent());

        assertTrue(name.equalsIgnoreCase(category.get().getName()));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "Test",
            "Stack",
            "Graph",
            "1",
            "$"
    })
    void shouldNotFindByName(String name) {
        assertFalse(categoryRepository.findByName(name).isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Tree",
            "tree",
            "TREE",
            "TrEe",
            "treE",
            "hash table",
            "Hash Table",
            "HASH TABLE"
    })
    void shouldFindByNameCaseInsensitive(String name) {
        Optional<Category> category = categoryRepository.findByName(name);

        assertTrue(category.isPresent());

        assertTrue(name.equalsIgnoreCase(category.get().getName()));
    }
}
