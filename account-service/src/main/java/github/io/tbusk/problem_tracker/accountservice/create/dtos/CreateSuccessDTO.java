package github.io.tbusk.problem_tracker.accountservice.create.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data transfer object representing a successful account creation response.
 *
 * @param message the confirmation message to return to the user
 */
public record CreateSuccessDTO (
        @Schema(description = "The confirmation message to return to the user", example = "Account successfully created! Please log in.")
        String message
){
}
