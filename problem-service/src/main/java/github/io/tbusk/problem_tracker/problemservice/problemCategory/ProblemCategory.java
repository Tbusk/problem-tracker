package github.io.tbusk.problem_tracker.problemservice.problemCategory;

import github.io.tbusk.problem_tracker.problemservice.category.Category;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Represents data from the PROBLEM_CATEGORY table in the database, which maps a competitive programming problem
 * to a category, e.g., the FizzBuzz problem being mapped to the String category.
 */
@Entity
@Table(name = "PROBLEM_CATEGORY")
@IdClass(ProblemCategoryID.class)
public class ProblemCategory implements Serializable {

    /**
     * The competitive programming problem, e.g., FizzBuzz
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "PROBLEM_ID", nullable = false)
    @NotNull
    private Problem problem;

    /**
     * The category of the problem, e.g., String
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    @NotNull
    private Category category;

    /**
     * Constructs a new ProblemCategory instance that maps a competitive programming problem to a category.
     *
     * @param problem  the competitive programming problem, e.g., FizzBuzz.
     * @param category the category of the problem, e.g., String.
     */
    public ProblemCategory(Problem problem, Category category) {
        this.problem = problem;
        this.category = category;
    }

    public ProblemCategory() {

    }

    /**
     * Gets the competitive programming problem, e.g., FizzBuzz
     *
     * @return the problem
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Gets the category of the problem, e.g., String
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Updates the competitive programming problem
     *
     * @param problem the new problem
     */
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * Updates the category of the problem
     *
     * @param category the new category
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}
