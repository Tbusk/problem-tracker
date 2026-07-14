package github.io.tbusk.problem_tracker.problemservice.programmingLanguage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Collection;

//TODO: add javadoc for ProgrammingLanguageRepository
@org.springframework.stereotype.Repository
public interface ProgrammingLanguageRepository extends Repository<ProgrammingLanguage, Short> {

    @Query("select name from ProgrammingLanguage")
    Collection<String> findAll();

}
