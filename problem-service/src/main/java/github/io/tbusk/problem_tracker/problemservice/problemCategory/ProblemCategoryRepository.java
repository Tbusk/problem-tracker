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
     * Finds all categories associated with a given problem
     *
     * @param id the id of the problem whose categories to retrieve
     * @return a collection of problem-category mappings for the problem
     */
    Collection<ProblemCategory> findAllByProblem_Id(Integer id);

}
