package github.io.tbusk.problem_tracker.problemgateway.jwt;

import github.io.tbusk.problem_tracker.problemgateway.user.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

/**
 * Service responsible for creating, validating, and extracting claims from JWT tokens.
 */
@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

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
     * Creates a signed JWT token for the given user containing their id, email, and role as claims.
     * The token is valid for the configured number of hours.
     *
     * @param user the user to create a token for
     * @return the signed JWT token string
     */
    public String createToken(User user) {

        LocalDateTime nDaysFromNowUTC = LocalDateTime.now(ZoneOffset.UTC).plusHours(hoursValid);

        return Jwts.builder()
                .subject(user.getEmailAddress())
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(Date.from(nDaysFromNowUTC.toInstant(ZoneOffset.UTC)))
                .claims(Map.of(
                        "id", user.getId(),
                        "emailAddress", user.getEmailAddress(),
                        "role", user.getRole().getName()
                ))
                .signWith(getKey())
                .compact();
    }

    /**
     * Validates whether the given JWT token is legitimate and unexpired.
     *
     * @param token the JWT token to validate
     * @return true if the token is valid and can be parsed, false otherwise
     */
    public boolean validateToken(String token) {
        return getClaims(token) != null;
    }

    /**
     * Extracts the claims from the given JWT token.
     * Returns null if the token is malformed, expired, or otherwise invalid.
     *
     * @param token the JWT token to parse
     * @return the claims extracted from the token, or null if parsing fails
     */
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token '{}'", token, e);
        } catch (ExpiredJwtException e) {
            log.warn("Jwt expired '{}'", token, e);
        } catch (JwtException e) {
            log.warn("Invalid JWT token '{}'", token, e);
        }

        return null;
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
