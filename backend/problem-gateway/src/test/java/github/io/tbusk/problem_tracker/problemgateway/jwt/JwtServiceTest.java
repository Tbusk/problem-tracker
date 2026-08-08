package github.io.tbusk.problem_tracker.problemgateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private final JwtService jwtService;

    public JwtServiceTest() {
        this.jwtService = new JwtService();
    }

    private String makeToken(String algorithm, String jwtKey, String emailAddress, int expiresInMinutes, String issuer, String roleName, int accountID) {

        SecretKey key = new SecretKeySpec(jwtKey.getBytes(), algorithm);

        return Jwts.builder()
                .subject(emailAddress)
                .issuer(issuer)
                .issuedAt(new Date())
                .signWith(key)
                .expiration(new Date(Instant.now().plus(expiresInMinutes, ChronoUnit.MINUTES).toEpochMilli()))
                .claims(Map.of(
                        "id", accountID,
                        "emailAddress", emailAddress,
                        "role", roleName
                ))
                .compact();
    }

    @Test
    void shouldValidateToken() {

        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        assertTrue(jwtService.validateToken(jwtToken));
    }

    @Test
    void shouldReturnFalseWithWrongAlgorithm() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", "HmacSHA128");
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        assertFalse(jwtService.validateToken(jwtToken));
    }

    @Test
    void shouldReturnFalseWithWrongSecretKey() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", "xK9mP2nQ5vR8sT1wU4yZ7bC0dF3gH6jL");
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        assertFalse(jwtService.validateToken(jwtToken));
    }

    @Test
    void shouldReturnFalseWithExpiredToken() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, -5, issuer, roleName, accountID);

        assertFalse(jwtService.validateToken(jwtToken));
    }

    @Test
    void shouldReturnFalseWithTamperedToken() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        assertFalse(jwtService.validateToken(jwtToken.substring(0, jwtToken.length() - 1)));
    }

    @Test
    void shouldReturnFalseWithWrongIssuer() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, "ProblemsRUs", roleName, accountID);

        assertFalse(jwtService.validateToken(jwtToken));
    }

    @Test
    void shouldReturnFalseWithMissingIssuerFromClass() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        assertFalse(jwtService.validateToken(jwtToken));
    }

    @Test
    void shouldReturnFalseWithMissingIssuerFromToken() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String roleName = "USER";
        String issuer = "Problem Tracker";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, "", roleName, accountID);

        assertFalse(jwtService.validateToken(jwtToken));
    }

    @Test
    void shouldReturnFalseWithNullToken() {
        assertFalse(jwtService.validateToken(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "test",
            "fake.key.test"
    })
    void shouldReturnFalseWithFakeToken(String token) {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm",  algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer",  issuer);

        assertFalse(jwtService.validateToken(token));
    }

    @Test
    void shouldReturnClaimsWithEmailAsSubject() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        Claims claims = jwtService.getClaims(jwtToken);

        assertEquals(emailAddress, claims.getSubject());
    }

    @Test
    void shouldReturnClaimsWithProvidedIssuer() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        Claims claims = jwtService.getClaims(jwtToken);

        assertEquals(issuer, claims.getIssuer());
    }

    @Test
    void shouldReturnClaimsWithEmailAddress() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        Claims claims = jwtService.getClaims(jwtToken);

        assertEquals(emailAddress, claims.get("emailAddress", String.class));
    }

    @Test
    void shouldReturnClaimsWithAccountID() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        Claims claims = jwtService.getClaims(jwtToken);

        assertEquals(accountID, claims.get("id", Integer.class));
    }

    @Test
    void shouldReturnClaimsWithRole() {
        String jwtKey = "CAcCrFzykqzskkN4y7Lyf6EnfuEBH3EN";
        String algorithm = "HmacSHA256";
        String issuer = "Problem Tracker";
        String roleName = "USER";
        int accountID = 1;

        ReflectionTestUtils.setField(jwtService, "jwtKey", jwtKey);
        ReflectionTestUtils.setField(jwtService, "algorithm", algorithm);
        ReflectionTestUtils.setField(jwtService, "issuer", issuer);

        String emailAddress = "test@example.com";

        String jwtToken = makeToken(algorithm, jwtKey, emailAddress, 5, issuer, roleName, accountID);

        Claims claims = jwtService.getClaims(jwtToken);

        assertEquals(roleName, claims.get("role", String.class));
    }
}
