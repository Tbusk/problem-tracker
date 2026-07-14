package github.io.tbusk.problem_tracker.problemservice.platform;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

//TODO: add javadoc for PlatformRepository
@org.springframework.stereotype.Repository
public interface PlatformRepository extends Repository<Platform, Short> {

    @Query("select name from Platform")
    Collection<String> findAll();

    Optional<Platform> findByName(String name);

}
