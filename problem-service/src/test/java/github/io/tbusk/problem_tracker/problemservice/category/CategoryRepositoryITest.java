package github.io.tbusk.problem_tracker.problemservice.category;

import github.io.tbusk.problem_tracker.problemservice.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class CategoryRepositoryITest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAll_returns_expected() {
        Set<String> categories = new HashSet<>(categoryRepository.findAll());

        assertEquals(71, categories.size());

        assertTrue(categories.contains("Array"));
        assertTrue(categories.contains("String"));
        assertTrue(categories.contains("Tree"));
        assertTrue(categories.contains("Hash Table"));
        assertTrue(categories.contains("Stack"));
        assertTrue(categories.contains("Linked List"));
        assertTrue(categories.contains("Queue"));
        assertTrue(categories.contains("Binary Search Tree"));
    }

    @Test
    void findByName_returns_expected() {
        assertTrue(categoryRepository.findByName("Array").isPresent());
        assertTrue(categoryRepository.findByName("String").isPresent());
        assertTrue(categoryRepository.findByName("Tree").isPresent());
        assertTrue(categoryRepository.findByName("Hash Table").isPresent());
        assertTrue(categoryRepository.findByName("Stack").isPresent());
        assertTrue(categoryRepository.findByName("Linked List").isPresent());
        assertTrue(categoryRepository.findByName("Queue").isPresent());
        assertTrue(categoryRepository.findByName("Binary Search Tree").isPresent());

        assertFalse(categoryRepository.findByName("").isPresent());
        assertFalse(categoryRepository.findByName("Test").isPresent());
    }
}
