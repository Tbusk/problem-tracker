package github.io.tbusk.problem_tracker.problemservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * Represents data from the PLATFORM table in the database, which contains platforms the competitive programming
 * problem originates from, such as Leetcode or HackerRank.
 */
@Entity
@Table(name = "PLATFORM")
public class Platform {

    /**
     * Surrogate key representing the internal id for the platform
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "platform_seq", sequenceName = "PLATFORM_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Short id;

    /**
     * The user-facing name of the platform, e.g., Leetcode
     */
    @Column(name = "NAME", unique = true, nullable = false)
    @NotNull
    @Size(min = 2, max = 128)
    private String name;

    /**
     * Gets the platform id
     *
     * @return the platform id
     */
    public Short getId() {
        return id;
    }

    /**
     * Gets the platform name, e.g., Leetcode
     *
     * @return the platform name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the platform name
     *
     * @param name the new platform name
     */
    public void setName(String name) {
        this.name = name;
    }
}
