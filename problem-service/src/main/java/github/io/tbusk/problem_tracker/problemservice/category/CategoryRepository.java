package github.io.tbusk.problem_tracker.problemservice.category;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

//TODO: add javadoc for CategoryRepository
@org.springframework.stereotype.Repository
public interface CategoryRepository extends Repository<Category, Byte> {

    @Query("select name from Category")
    Collection<String> findAll();

    Optional<Category> findByName(String name);
}
