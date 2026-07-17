package github.io.tbusk.problem_tracker.problemgateway.jwt.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data transfer object for JWT token creation requests, containing credentials for authentication.
 *
 * @param emailAddress the user's email address, e.g., example@email.com
 * @param password     the user's password
 */
public record CreateJwtRequest(
        @Schema(description = "The user's email address", example = "example@email.com")
        String emailAddress,

        @Schema(description = "The user's password", example = "MySecret123!")
        String password
) {
}
