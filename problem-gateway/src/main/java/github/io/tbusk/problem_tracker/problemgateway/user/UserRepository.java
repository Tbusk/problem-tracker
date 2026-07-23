package github.io.tbusk.problem_tracker.problemgateway.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Repository for interacting with user account data from the database.
 */
@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param emailAddress the email address to search for
     * @return an {@link Optional} containing the user if found, or an empty {@link Optional} otherwise
     */
    @Query("select u from User u where lower(u.emailAddress) = lower(:emailAddress)")
    Optional<User> findByEmailAddress(String emailAddress);

}
