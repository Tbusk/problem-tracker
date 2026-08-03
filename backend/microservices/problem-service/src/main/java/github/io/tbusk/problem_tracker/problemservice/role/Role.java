package github.io.tbusk.problem_tracker.problemservice.role;

import jakarta.persistence.*;

/**
 * Represents data from the ROLE table in the database, which contains information about roles in the competitive
 * programming problem tracker, e.g., name
 */
@Entity
@Table(name = "ROLE")
public class Role {

    /**
     * Surrogate key representing the internal id for the role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_seq", sequenceName = "ROLE_SEQ", allocationSize = 1)
    private Byte id;

    /**
     * The name of the role, e.g., ADMIN
     */
    @Column(name = "NAME", nullable = false)
    private String name;


    /**
     * Gets the role id
     * @return the role id
     */
    public Byte getId() {
        return id;
    }

    /**
     * Gets the role name
     * @return the role name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the role name
     * @param name the new role name
     */
    public void setName(String name) {
        this.name = name;
    }
}
