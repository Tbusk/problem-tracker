package github.io.tbusk.problem_tracker.problemservice.difficulty;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

//TODO: add javadoc for DifficultyRepository
@org.springframework.stereotype.Repository
public interface DifficultyRepository extends Repository<Difficulty, Byte> {

    @Query("select name from Difficulty")
    Collection<String> findAll();

    Optional<Difficulty> findByName(String name);
}
