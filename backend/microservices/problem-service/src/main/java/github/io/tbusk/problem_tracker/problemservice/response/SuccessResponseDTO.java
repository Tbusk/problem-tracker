package github.io.tbusk.problem_tracker.problemservice.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO object carrying a success response with a message to a user.
 * @param message message to pass along to user
 */
public record SuccessResponseDTO(
        @Schema(description = "The message to pass along to the user.", example = "Problem successfully added.")
        String message
) {
}
