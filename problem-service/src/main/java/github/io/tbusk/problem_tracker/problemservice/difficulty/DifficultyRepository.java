package github.io.tbusk.problem_tracker.problemservice.difficulty;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository for interacting with difficulty level data from the database, such as Easy or Hard ratings.
 */
@org.springframework.stereotype.Repository
public interface DifficultyRepository extends Repository<Difficulty, Byte> {

    /**
     * Retrieves all difficulty level names from the database
     *
     * @return a collection of difficulty level names
     */
    @Query("select name from Difficulty")
    Collection<String> findAll();

    /**
     * Finds a difficulty level by its exact name
     *
     * @param name the difficulty name to search for, e.g., "Easy"
     * @return an Optional containing the difficulty if found, otherwise empty
     */
    Optional<Difficulty> findByName(String name);
}
