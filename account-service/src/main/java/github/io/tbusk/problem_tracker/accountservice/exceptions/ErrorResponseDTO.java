package github.io.tbusk.problem_tracker.accountservice.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data transfer object used for returning error information from REST endpoints.
 *
 * @param message a user-facing description of the error
 */
public record ErrorResponseDTO(
        @Schema(description = "A user-facing description of the error", example = "Invalid password supplied")
        String message
) {
}
