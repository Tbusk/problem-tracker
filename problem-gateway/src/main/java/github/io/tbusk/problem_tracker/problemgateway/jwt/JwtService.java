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

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.algorithm}")
    private String algorithm;

    @Value("${jwt.hours-valid}")
    private int hoursValid;

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
