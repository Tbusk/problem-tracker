package github.io.tbusk.problem_tracker.problemservice.problemCategory;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the PROBLEM_CATEGORY table, consisting of the problem and category
 * identifiers.
 */
public class ProblemCategoryID implements Serializable {

    /**
     * The surrogate id of the competitive programming problem
     */
    private Integer problem;

    /**
     * The surrogate id of the category
     */
    private Byte category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProblemCategoryID that)) return false;
        return Objects.equals(problem, that.problem) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(problem, category);
    }
}
