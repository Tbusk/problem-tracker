package github.io.tbusk.problem_tracker.problemservice.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository for interacting with category data from the database, such as String or Queue categories.
 */
@org.springframework.stereotype.Repository
public interface CategoryRepository extends Repository<Category, Byte> {

    /**
     * Retrieves all category names from the database
     *
     * @return a collection of category names
     */
    @Query("select name from Category")
    Collection<String> findAllNames();

    /**
     * Retrieves all categories from the database
     *
     * @return a collection of categories
     */
    Collection<Category> findAll();

    /**
     * Finds a category by its exact name
     *
     * @param name the category name to search for, e.g., "String"
     * @return an Optional containing the category if found, otherwise empty
     */
    Optional<Category> findByName(String name);
}
