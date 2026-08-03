package github.io.tbusk.problem_tracker.accountservice.create.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data transfer object for account creation requests, containing the user's email address and password.
 *
 * @param emailAddress the user's email address, e.g., example@email.com
 * @param password     the user's desired password
 */
public record CreateRequestDTO(
        @Schema(description = "The user's email address", example = "example@email.com")
        String emailAddress,

        @Schema(description = "The user's desired password", example = "MySecret123!")
        String password
) {
}
