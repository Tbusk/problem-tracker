package github.io.tbusk.problem_tracker.problemservice.difficulty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents data from the DIFFICULTY table in the database, which contains how difficult a problem is rated on the
 * competitive programming platform, such as Easy or Hard.
 */
@Entity
@Table(name = "DIFFICULTY")
public class Difficulty {

    /**
     * The surrogate key representing an internal id for the difficulty level
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "difficulty_seq", sequenceName = "DIFFICULTY_SEQ", allocationSize = 1)
    private Byte id;

    /**
     * The user-facing name of the difficulty level
     */
    @Column(name = "NAME", nullable = false, unique = true)
    @Size(min = 2, max = 128)
    @NotNull
    private String name;

    /**
     * Gets the difficulty level id
     *
     * @return the difficulty level id
     */
    public Byte getId() {
        return id;
    }

    /**
     * Gets the difficulty level name, e.g., Easy
     *
     * @return the difficulty level name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the difficulty level name
     *
     * @param name the new difficulty name
     */
    public void setName(String name) {
        this.name = name;
    }
}
