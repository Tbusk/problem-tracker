package github.io.tbusk.problem_tracker.accountservice.role;

import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface RoleRepository extends Repository<Role, Long> {
    Optional<Role> findByName(String name);
}
