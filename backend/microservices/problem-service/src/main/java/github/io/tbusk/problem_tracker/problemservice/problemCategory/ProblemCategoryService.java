package github.io.tbusk.problem_tracker.problemservice.problemCategory;

import github.io.tbusk.problem_tracker.problemservice.category.Category;
import github.io.tbusk.problem_tracker.problemservice.category.CategoryRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service responsible for creating and persisting problem-category associations.
 */
@Service
public class ProblemCategoryService {

    private ProblemCategoryRepository problemCategoryRepository;
    private CategoryRepository categoryRepository;

    /**
     * Creates a service instance with the required repositories.
     *
     * @param problemCategoryRepository repository for persisting problem-category associations
     * @param categoryRepository        repository for resolving categories
     */
    public ProblemCategoryService(ProblemCategoryRepository problemCategoryRepository, CategoryRepository categoryRepository) {
        this.problemCategoryRepository = problemCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates problem-category associations for each valid category in the given list.
     * Only categories that already exist in the database are associated; unknown category names are silently ignored.
     *
     * @param problem    the problem to associate categories with
     * @param categories the list of category names to associate
     * @return the collection of persisted problem-category associations
     * @throws IllegalArgumentException if the categories list is null or empty
     */
    public Collection<ProblemCategory> createAll(Problem problem, List<String> categories) {

        if (categories == null) {
            throw new IllegalArgumentException("Categories cannot be empty");
        }

        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Please specify at least one category");
        }

        Collection<Category> existingCategories = categoryRepository.findAll();
        Set<String> categoryNames = new HashSet<>(categories);
        Collection<ProblemCategory> categoriesToAdd = new ArrayList<>();

        for (Category category : existingCategories) {
            if (categoryNames.contains(category.getName())) {
                ProblemCategory problemCategory = new ProblemCategory(problem, category);

                categoriesToAdd.add(problemCategory);
            }
        }

        return problemCategoryRepository.saveAll(categoriesToAdd);
    }


}
