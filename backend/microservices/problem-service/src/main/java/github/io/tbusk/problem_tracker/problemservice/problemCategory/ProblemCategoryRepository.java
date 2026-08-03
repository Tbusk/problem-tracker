package github.io.tbusk.problem_tracker.problemservice.problemCategory;

import org.springframework.data.repository.Repository;

import java.util.Collection;

/**
 * Repository for interacting with problem-category mappings from the database, linking a problem to its categories
 * such as mapping FizzBuzz to the String category.
 */
@org.springframework.stereotype.Repository
public interface ProblemCategoryRepository extends Repository<ProblemCategory, ProblemCategoryID> {

    /**
     * Saves a problem-category mapping to the database
     *
     * @param problemCategory the mapping to persist
     * @return the saved mapping
     */
    ProblemCategory save(ProblemCategory problemCategory);

    /**
     * Saves a collection of problem-category mappings to the database
     *
     * @param problemCategories the mappings to persist
     * @return the saved mappings
     */
    Collection<ProblemCategory> saveAll(Iterable<ProblemCategory> problemCategories);
}
