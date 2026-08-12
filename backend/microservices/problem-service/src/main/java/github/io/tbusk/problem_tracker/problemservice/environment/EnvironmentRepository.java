package github.io.tbusk.problem_tracker.problemservice.environment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Repository for interacting with environment data from the database, such as Pen and Paper or Full IDE.
 */
@org.springframework.stereotype.Repository
public interface EnvironmentRepository extends Repository<Environment, Byte> {

    /**
     * Finds an environment by its name (case-insensitive)
     *
     * @param name the name of the environment, e.g., "Pen and Paper"
     * @return an optional containing the environment if found, or an empty optional if not found
     */
    @Query("select e from Environment e where lower(e.name) = lower(:name)")
    Optional<Environment> findByName(String name);
}