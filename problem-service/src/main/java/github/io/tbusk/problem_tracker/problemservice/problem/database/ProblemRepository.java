package github.io.tbusk.problem_tracker.problemservice.problem.database;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
}
