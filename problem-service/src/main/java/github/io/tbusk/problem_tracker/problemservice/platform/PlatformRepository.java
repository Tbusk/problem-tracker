package github.io.tbusk.problem_tracker.problemservice.platform;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository for interacting with platform data from the database, such as Leetcode or HackerRank.
 */
@org.springframework.stereotype.Repository
public interface PlatformRepository extends Repository<Platform, Short> {

    /**
     * Retrieves all platform names from the database
     *
     * @return a collection of platform names
     */
    @Query("select name from Platform")
    Collection<String> findAll();

    /**
     * Finds a platform by its exact name
     *
     * @param name the platform name to search for, e.g., "Leetcode"
     * @return an Optional containing the platform if found, otherwise empty
     */
    Optional<Platform> findByName(String name);

}
