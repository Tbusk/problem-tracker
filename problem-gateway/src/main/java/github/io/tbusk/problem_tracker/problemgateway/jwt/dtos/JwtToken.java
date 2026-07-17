package github.io.tbusk.problem_tracker.problemgateway.jwt.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data transfer object representing a JWT token returned after successful authentication.
 *
 * @param token the signed JWT token string
 */
public record JwtToken(
        @Schema(description = "The signed JWT token string", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token
) {
}
