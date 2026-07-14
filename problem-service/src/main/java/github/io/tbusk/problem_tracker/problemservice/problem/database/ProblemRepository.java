package github.io.tbusk.problem_tracker.problemservice.problem.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for interacting with competitive programming problem data from the database.
 */
@Repository
public interface ProblemRepository extends PagingAndSortingRepository<Problem, Integer> {

    /**
     * Saves a problem to the database, creating a new or updating an existing problem
     *
     * @param problem the problem to persist
     * @return the saved problem
     */
    Problem save(Problem problem);

    /**
     * Finds a problem by its details
     * <p>
     * Used primarily to check if a problem already exists in the database to prevent duplicates.
     *
     * @param name the name of the problem
     * @param url the url of the problem
     * @param difficulty the difficulty of the problem
     * @param platform the platform the problem is from
     * @return an Optional containing the problem if found, otherwise empty
     */
    @Query("select p from Problem p where p.name = :name and p.url = :url and p.difficulty = :difficulty and p.platform = :platform limit 1")
    Optional<Problem> findByDetails(String name, String url, String difficulty, String platform);
}
