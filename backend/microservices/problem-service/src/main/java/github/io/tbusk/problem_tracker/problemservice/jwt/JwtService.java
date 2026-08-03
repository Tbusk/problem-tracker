package github.io.tbusk.problem_tracker.problemservice.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Service responsible for validating JWT tokens and extracting claims for request authentication.
 */
@Service
public class JwtService {

    /**
     * The secret key used to sign and verify JWT tokens, sourced from application configuration.
     */
    @Value("${jwt.key}")
    private String jwtKey;

    /**
     * The cryptographic algorithm used for JWT signing and verification, e.g., HmacSHA256.
     */
    @Value("${jwt.algorithm}")
    private String algorithm;

    private Logger log = org.slf4j.LoggerFactory.getLogger(JwtService.class);

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
     * @return the secret key used for token verification
     */
    private SecretKey getKey() {
        return new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), algorithm);
    }
}