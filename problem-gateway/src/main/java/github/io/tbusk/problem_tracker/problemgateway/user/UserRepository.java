package github.io.tbusk.problem_tracker.problemgateway.user;

import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Repository for interacting with user account data from the database.
 */
@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByEmailAddress(String emailAddress);

}
