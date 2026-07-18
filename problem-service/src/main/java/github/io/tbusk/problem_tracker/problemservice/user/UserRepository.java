package github.io.tbusk.problem_tracker.problemservice.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    /**
     * Finds a user by their email address
     *
     * @param emailAddress the email address of the user
     * @return the user with the given email address, or an empty optional if no user exists with that email address
     */
    Optional<User> findByEmailAddress(String emailAddress);

}
