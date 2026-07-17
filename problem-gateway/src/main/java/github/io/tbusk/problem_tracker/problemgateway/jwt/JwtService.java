package github.io.tbusk.problem_tracker.problemgateway.jwt;

import github.io.tbusk.problem_tracker.problemgateway.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        return new SecretKeySpec(jwtKey.getBytes(StandardCharsets.UTF_8), algorithm);
    }
}
