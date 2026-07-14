package github.io.tbusk.problem_tracker.problemservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents data from the PROGRAMMING_LANGUAGE table in the database, which contains the programming language used to
 * solve a particular competitive programming problem, e.g., Java.
 */
@Entity
@Table(name = "PROGRAMMING_LANGUAGE")
public class ProgrammingLanguage {

    /**
     * Surrogate key representing the internal id for the programming language
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "programming_language_seq", sequenceName = "PROGRAMMING_LANGUAGE_SEQ", allocationSize = 1)
    private Short id;

    /**
     * The user-facing name of the programming language, e.g., Java
     */
    @Column(name = "NAME", nullable = false, unique = true)
    @Size(min = 2, max = 128)
    @NotNull
    private String name;

    /**
     * Gets the programming language id
     *
     * @return the programming language id
     */
    public Short getId() {
        return id;
    }

    /**
     * Gets the programming language name
     *
     * @return the programming language name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the programming language name
     *
     * @param name the new programming language name
     */
    public void setName(String name) {
        this.name = name;
    }
}
