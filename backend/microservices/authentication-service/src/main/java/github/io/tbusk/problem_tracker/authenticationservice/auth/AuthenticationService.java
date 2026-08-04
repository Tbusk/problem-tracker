package github.io.tbusk.problem_tracker.authenticationservice.auth;

import github.io.tbusk.problem_tracker.authenticationservice.auth.dtos.CreateJwtRequest;
import github.io.tbusk.problem_tracker.authenticationservice.auth.dtos.JwtToken;
import github.io.tbusk.problem_tracker.authenticationservice.user.User;
import github.io.tbusk.problem_tracker.authenticationservice.user.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Service responsible for authenticating users by verifying their credentials against the database
 * and issuing JWT tokens.
 */
@Service
public class AuthenticationService {

    private UserRepository userRepository;

    /**
     * The secret key used to sign and verify JWT tokens, sourced from application configuration.
     */
    @Value("${jwt.key}")
    private String jwtKey;

    /**
     * The issuer of the JWT token, sourced from application configuration.
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * The cryptographic algorithm used for JWT signing and verification, e.g., HmacSHA256.
     */
    @Value("${jwt.algorithm}")
    private String algorithm;

    /**
     * The number of hours a newly created JWT token remains valid, sourced from application configuration.
     */
    @Value("${jwt.hours-valid}")
    private int hoursValid;

    /**
     * Creates a service instance with the required repositories.
     *
     * @param userRepository repository for looking up user accounts
     */
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        String jwtToken = createToken(user.getEmailAddress(), user.getId(), user.getRole().getName());

        return new JwtToken(jwtToken);
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

        if (!emailAddress.matches("^[a-zA-Z0-9]+(\\.?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]{2,})+$")) {
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

    /**
     * Creates a signed JWT token for the given user containing their id, email, and role as claims.
     * The token is valid for the configured number of hours.
     *
     * @param emailAddress the email address to set as the subject and claim of the token
     * @param accountID the account id to include as a claim of the token
     * @param roleName the role name to include as a claim of the token
     * @return the signed JWT token string
     */
    private String createToken(String emailAddress, Long accountID, String roleName) {

        LocalDateTime nDaysFromNowUTC = LocalDateTime.now(ZoneOffset.UTC).plusHours(hoursValid);

        return Jwts.builder()
                .subject(emailAddress)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(Date.from(nDaysFromNowUTC.toInstant(ZoneOffset.UTC)))
                .claims(Map.of(
                        "id", accountID,
                        "emailAddress", emailAddress,
                        "role", roleName
                ))
                .signWith(getKey())
                .compact();
    }

    /**
     * Derives a {@link SecretKey} from the configured JWT key and algorithm.
     *
     * @return the secret key used for token signing and verification
     */
    private SecretKey getKey() {
        return new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), algorithm);
    }
}
