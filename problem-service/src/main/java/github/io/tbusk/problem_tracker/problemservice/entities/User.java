package github.io.tbusk.problem_tracker.problemservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Represents data from the USER table in the database, which contains the user accounts for the competitive
 * programming problem tracker, e.g., email address and password.
 */
@Entity
@Table(name = "USER")
public class User {

    /**
     * Surrogate key representing the internal id for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ")
    private Long id;

    /**
     * The email address of the user, e.g., example@email.com
     */
    @Column(name = "EMAIL_ADDRESS", nullable = false, unique = true)
    @Size(min = 5, max = 255)
    @NotNull
    private String emailAddress;

    /**
     * The timestamp of when the user account was created
     */
    @Column(name = "CREATED_ON", nullable = false)
    @NotNull
    private LocalDateTime createdOn;

    /**
     * The hashed password for the user account
     */
    @Column(name = "PASSWORD_HASH", nullable = false)
    @Size(min = 16, max = 128)
    @NotNull
    private String passwordHash;

    /**
     * Whether the user account is enabled
     */
    @Column(name = "ENABLED", nullable = false)
    @NotNull
    private Boolean enabled;

    /**
     * Whether the user account is locked
     */
    @Column(name = "LOCKED", nullable = false)
    @NotNull
    private Boolean locked;

    /**
     * Gets the user id
     *
     * @return the user id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the user email address, e.g., example@email.com
     *
     * @return the user email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Updates the user email address
     *
     * @param emailAddress the new user email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the timestamp when the user account was created
     *
     * @return the user creation timestamp
     */
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    /**
     * Updates the user creation timestamp
     *
     * @param createdOn the new user creation timestamp
     */
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the hashed password for the user account
     *
     * @return the user password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Updates the user password hash
     *
     * @param passwordHash the new user password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Gets whether the user account is enabled
     *
     * @return the enabled status
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Updates whether the user account is enabled
     *
     * @param enabled the new enabled status
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets whether the user account is locked
     *
     * @return the locked status
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * Updates whether the user account is locked
     *
     * @param locked the new locked status
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
