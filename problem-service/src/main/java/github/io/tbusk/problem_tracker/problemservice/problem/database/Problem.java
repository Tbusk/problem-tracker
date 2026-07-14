package github.io.tbusk.problem_tracker.problemservice.problem.database;

import github.io.tbusk.problem_tracker.problemservice.difficulty.Difficulty;
import github.io.tbusk.problem_tracker.problemservice.platform.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents data from the PROBLEM table in the database, which contains information related to a competitive
 * programming problem on a platform, such as its name, url, the platform, and its difficulty level.
 */
@Entity
@Table(name = "PROBLEM")
public class Problem {

    /**
     * The surrogate key representing an internal id for the competitive programming problem
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "problem_seq", sequenceName = "PROBLEM_SEQ", allocationSize = 1)
    private Integer id;

    /**
     * The user-facing name of the competitive programming problem, e.g., FizzBuzz
     */
    @Column(name = "NAME", nullable = false)
    @Size(min = 2, max = 128)
    @NotNull
    private String name;

    /**
     * The url of the competitive programming problem, e.g., <a href="https://leetcode.com/problems/roman-to-integer">roman to integer (leetcode)</a>
     */
    @Column(name = "URL", nullable = false)
    @Size(min = 10, max = 512)
    @NotNull
    private String url;

    /**
     * The platform the competitive programming problem is on, e.g., Leetcode
     */
    @ManyToOne
    @JoinColumn(name = "PLATFORM_ID", nullable = false)
    @NotNull
    private Platform platform;

    /**
     * The difficulty of the competitive programming problem, e.g., Easy
     */
    @ManyToOne
    @JoinColumn(name = "DIFFICULTY_ID", nullable = false)
    @NotNull
    private Difficulty difficulty;

    /**
     * Gets the competitive programming problem id
     *
     * @return the problem id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the competitive programming problem name
     *
     * @return the problem name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the competitive programming problem name
     *
     * @param name the new problem name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the url of the competitive programming problem
     *
     * @return the problem url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Updates the competitive programming problem url
     *
     * @param url the new problem url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the competitive programming problem platform
     *
     * @return the problem platform
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Updates the competitive programming problem platform
     *
     * @param platform the new problem platform
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * Gets the competitive programming problem difficulty level
     *
     * @return the problem difficulty level
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Updates the competitive programming problem difficulty level
     *
     * @param difficulty the new problem difficulty level
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
