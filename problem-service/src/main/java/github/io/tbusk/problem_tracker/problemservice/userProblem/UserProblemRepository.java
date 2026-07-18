package github.io.tbusk.problem_tracker.problemservice.userProblem;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Repository for interacting with user problem records from the database, tracking when a user solved a
 * problem including the programming language used and time taken.
 */
@Repository
public interface UserProblemRepository extends PagingAndSortingRepository<UserProblem, Long> {

    /**
     * Retrieves all user problem records from the database
     *
     * @return a collection of all user problem records
     */
    Collection<UserProblem> findAll();

    /**
     * Saves a user problem record to the database, creating a new or updating an existing record
     *
     * @param userProblem the user problem to save
     * @return the saved user problem record
     */
    UserProblem save(UserProblem userProblem);
}
