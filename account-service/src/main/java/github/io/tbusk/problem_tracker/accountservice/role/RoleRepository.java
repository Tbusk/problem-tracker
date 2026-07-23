package github.io.tbusk.problem_tracker.accountservice.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * Repository for interacting with role data from the database.
 */
@org.springframework.stereotype.Repository
public interface RoleRepository extends Repository<Role, Long> {
    /**
     * Finds a role by its name.
     *
     * @param name the name of the role, e.g., ADMIN or USER
     * @return an Optional containing the role if found, otherwise empty
     */
    @Query("select r from Role r where lower(r.name) = lower(:name)")
    Optional<Role> findByName(String name);
}
