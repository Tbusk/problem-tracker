package github.io.tbusk.problem_tracker.problemservice.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for errors that are handled and returned to the user.
 * @param message the error message to share with the user
 */
public record ErrorResponseDTO (
        @Schema(description = "The error message to share with the user", example = "The name needs to be at least 2 characters in length")
        String message
) {
}
