package github.io.tbusk.problem_tracker.problemservice.problemCategory;

import github.io.tbusk.problem_tracker.problemservice.category.Category;
import github.io.tbusk.problem_tracker.problemservice.category.CategoryRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProblemCategoryService {

    private ProblemCategoryRepository problemCategoryRepository;
    private CategoryRepository categoryRepository;

    public ProblemCategoryService(ProblemCategoryRepository problemCategoryRepository, CategoryRepository categoryRepository) {
        this.problemCategoryRepository = problemCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

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
