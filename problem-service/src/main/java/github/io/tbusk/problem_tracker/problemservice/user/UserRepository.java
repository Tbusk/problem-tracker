package github.io.tbusk.problem_tracker.problemservice.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for interacting with user account data from the database.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository <User, Long> {

    /**
     * Saves a user to the database, creating a new or updating an existing user
     *
     * @param user the user to persist
     * @return the saved user
     */
    User save(User user);

}
