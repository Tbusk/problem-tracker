package github.io.tbusk.problem_tracker.problemservice.environment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents data from the ENVIRONMENT table in the database, which contains the environments a problem may be
 * worked on in, such as Pen and Paper or Full IDE.
 */
@Entity
@Table(name = "ENVIRONMENT")
public class Environment {

    /**
     * The surrogate key representing an internal id for the environment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "environment_seq", sequenceName = "ENVIRONMENT_SEQ", allocationSize = 1)
    private Byte id;

    /**
     * The user-facing name of the environment, e.g., Whiteboard
     */
    @Column(name = "NAME", nullable = false, unique = true)
    @Size(min = 2, max = 128)
    @NotNull
    private String name;

    /**
     * Gets the environment id
     *
     * @return the environment id
     */
    public Byte getId() {
        return id;
    }

    /**
     * Gets the environment name, e.g., Full IDE
     *
     * @return the environment name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the environment name
     *
     * @param name the new environment name
     */
    public void setName(String name) {
        this.name = name;
    }
}