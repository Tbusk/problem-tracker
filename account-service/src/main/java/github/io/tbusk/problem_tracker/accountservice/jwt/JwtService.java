package github.io.tbusk.problem_tracker.accountservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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