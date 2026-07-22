package github.io.tbusk.problem_tracker.problemgateway.jwt.auth;

import github.io.tbusk.problem_tracker.problemgateway.jwt.JwtService;
import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.CreateJwtRequest;
import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.JwtToken;
import github.io.tbusk.problem_tracker.problemgateway.user.User;
import github.io.tbusk.problem_tracker.problemgateway.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for authenticating users by verifying their credentials against the database
 * and issuing JWT tokens.
 */
@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private JwtService jwtService;

    /**
     * Creates a service instance with the required repositories and JWT service.
     *
     * @param userRepository repository for looking up user accounts
     * @param jwtService     service for creating JWT tokens
     */
    public AuthenticationService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    /**
     * Authenticates a user by their email and password.
     * Validates the email address format, checks the user exists and is not locked or disabled,
     * and verifies the password against the stored hash.
     *
     * @param createJwtRequest the credentials containing email and password
     * @return a JWT token for the authenticated user
     * @throws AuthenticationException  if the email or password is invalid, or the account is locked or disabled
     * @throws IllegalArgumentException if the request or any required field is null
     */
    public JwtToken authenticate(CreateJwtRequest createJwtRequest) throws AuthenticationException {

        if (createJwtRequest == null) {
            throw new IllegalArgumentException("The request body cannot be empty");
        }

        if (createJwtRequest.emailAddress() == null) {
            throw new IllegalArgumentException("The email address cannot be empty");
        }

        validateEmailAddress(createJwtRequest.emailAddress());

        Optional<User> potentialUser = userRepository.findByEmailAddress(createJwtRequest.emailAddress());

        if (!potentialUser.isPresent()) {
            throw new AuthenticationException("The email address or password is invalid");
        }

        User user = potentialUser.get();

        if (user.getLocked()) {
            throw new AuthenticationException("Your account is locked. Please contact support.");
        }

        if (!user.getEnabled()) {
            throw new AuthenticationException("Your account is disabled. Please contact support.");
        }

        if (createJwtRequest.password() == null) {
            throw new IllegalArgumentException("The password cannot be empty");
        }

        if (!validatePassword(user.getPasswordHash(), createJwtRequest.password())) {
            throw new AuthenticationException("The email address or password is invalid");
        }

        return new JwtToken(jwtService.createToken(user));
    }

    /**
     * Validates the format of the supplied email address.
     *
     * @param emailAddress the email address to validate
     * @return true if the email address is valid
     * @throws IllegalArgumentException if the email address is too long or does not match the expected pattern
     */
    private boolean validateEmailAddress(String emailAddress) {

        int maxLength = 255;

        if (emailAddress.length() > maxLength) {
            throw new IllegalArgumentException("The email address supplied is not valid");
        }

        if (!emailAddress.matches("^[a-zA-Z0-9]+(\\.?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$")) {
            throw new IllegalArgumentException("The email address supplied is not valid");
        }

        return true;
    }

    /**
     * Verifies a guessed password against the stored BCrypt hash.
     *
     * @param passwordHash    the stored hashed password
     * @param guessedPassword the password to verify
     * @return true if the password matches the hash, false otherwise
     */
    private boolean validatePassword(String passwordHash, String guessedPassword) {
        return BCrypt.checkpw(guessedPassword, passwordHash);
    }
}
