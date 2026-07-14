package github.io.tbusk.problem_tracker.problemservice.programmingLanguage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;

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

}
