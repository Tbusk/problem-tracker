package github.io.tbusk.problem_tracker.problemservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Represents data from the USER_PROBLEM table in the database, which tracks when a user solved a competitive
 * programming problem, including the programming language used and time taken.
 */
@Entity
@Table(name = "USER_PROBLEM")
public class UserProblem {

    /**
     * Surrogate key representing the internal id for the user problem record
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_problem_seq", sequenceName = "USER_PROBLEM_SEQ")
    private Long id;

    /**
     * The user who solved the competitive programming problem
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @NotNull
    private User user;

    /**
     * The competitive programming problem that was solved
     */
    @ManyToOne
    @JoinColumn(name = "PROBLEM_ID", nullable = false)
    @NotNull
    private Problem problem;

    /**
     * The timestamp of when the problem was solved
     */
    @Column(name = "SOLVED_ON", nullable = false)
    @NotNull
    private LocalDateTime solvedOn;

    /**
     * The number of minutes taken to solve the problem
     */
    @Column(name = "MINUTES", nullable = false)
    @NotNull
    private Float minutes;

    /**
     * The programming language used to solve the problem
     */
    @ManyToOne
    @JoinColumn(name = "PROGRAMMING_LANGUAGE_ID", nullable = false)
    @NotNull
    private ProgrammingLanguage programmingLanguage;

    /**
     * Gets the user problem record id
     *
     * @return the user problem record id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the user who solved the problem
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Updates the user who solved the problem
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the competitive programming problem that was solved
     *
     * @return the problem
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Updates the competitive programming problem that was solved
     *
     * @param problem the new problem
     */
    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    /**
     * Gets the timestamp when the problem was solved
     *
     * @return the solved timestamp
     */
    public LocalDateTime getSolvedOn() {
        return solvedOn;
    }

    /**
     * Updates the timestamp when the problem was solved
     *
     * @param solvedOn the new solved timestamp
     */
    public void setSolvedOn(LocalDateTime solvedOn) {
        this.solvedOn = solvedOn;
    }

    /**
     * Gets the number of minutes taken to solve the problem
     *
     * @return the minutes taken
     */
    public Float getMinutes() {
        return minutes;
    }

    /**
     * Updates the number of minutes taken to solve the problem
     *
     * @param minutes the new minutes value
     */
    public void setMinutes(Float minutes) {
        this.minutes = minutes;
    }

    /**
     * Gets the programming language used to solve the problem
     *
     * @return the programming language
     */
    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    /**
     * Updates the programming language used to solve the problem
     *
     * @param programmingLanguage the new programming language
     */
    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
}
