package github.io.tbusk.problem_tracker.problemservice.userProblem;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO object for adding a user-completed problem record
 *
 * @param problemName the name of the problem
 * @param platformName the name of the platform used to solve the problem
 * @param programmingLanguage the programming language used to solve the problem
 * @param minutes the number of minutes spent solving the problem
 */
public record AddUserProblemDTO(
        @Schema(description = "Name of the problem", example = "FizzBuzz")
        String problemName,

        @Schema(description = "Name of the platform used to solve the problem", example = "LeetCode")
        String platformName,

        @Schema(description = "Programming language used to solve the problem", example = "Java")
        String programmingLanguage,

        @Schema(description = "Number of minutes spent solving the problem", example = "1.5")
        float minutes
) {
}
