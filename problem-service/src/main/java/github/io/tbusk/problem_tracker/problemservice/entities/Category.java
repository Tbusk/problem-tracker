package github.io.tbusk.problem_tracker.problemservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents data from the CATEGORY table in the database, which contains the categories for competitive programming
 * problems, such as String or Queue.
 */
@Entity
@Table(name = "CATEGORY")
public class Category {

    /**
     * The surrogate key representing an internal id for the category
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_seq", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    private Byte id;

    /**
     * The user-facing name of the category, e.g., HashMap
     */
    @Column(name = "NAME", unique = true, nullable = false)
    @Size(min = 2, max = 128)
    @NotNull
    private String name;

    /**
     * Gets the category id
     *
     * @return the category id
     */
    public Byte getId() {
        return id;
    }

    /**
     * Gets the category name, e.g., String
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the category name
     *
     * @param name the new category name
     */
    public void setName(String name) {
        this.name = name;
    }
}
