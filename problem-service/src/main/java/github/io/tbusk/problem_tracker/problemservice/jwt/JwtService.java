package github.io.tbusk.problem_tracker.problemservice.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.algorithm}")
    private String algorithm;

    private Logger log = org.slf4j.LoggerFactory.getLogger(JwtService.class);

    public boolean validateToken(String token) {
        return getClaims(token) != null;
    }

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

    private SecretKey getKey() {
        return new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), algorithm);
    }
}