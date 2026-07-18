package github.io.tbusk.problem_tracker.problemservice.programmingLanguage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository for interacting with programming language data from the database, such as Java or Python.
 */
@org.springframework.stereotype.Repository
public interface ProgrammingLanguageRepository extends Repository<ProgrammingLanguage, Short> {

    /**
     * Retrieves all programming language names from the database
     *
     * @return a collection of programming language names
     */
    @Query("select name from ProgrammingLanguage")
    Collection<String> findAll();

    /**
     * Finds a programming language by name
     *
     * @param name the name of the programming language to find
     * @return an optional containing the programming language if found, or an empty optional if not found
     */
    Optional<ProgrammingLanguage> findByName(String name);
}
