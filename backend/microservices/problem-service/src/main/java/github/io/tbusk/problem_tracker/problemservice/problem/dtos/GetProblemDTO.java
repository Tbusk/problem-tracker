package github.io.tbusk.problem_tracker.problemservice.problem.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO used in the request to get a problem
 *
 * @param name the name of the problem, e.g., FizzBuzz
 * @param url the url of the problem, e.g., <a href="https://leetcode.com/problems/fizz-buzz/">FizzBuzz</a>
 * @param platformName the platform name the problem is on, e.g., Leetcode
 * @param difficulty the difficulty level of the problem, e.g., Easy
 */
public record GetProblemDTO(
        @Schema(description = "The problem's name", example = "FizzBuzz")
        String name,

        @Schema(description = "The problem's url", example = "https://leetcode.com/problems/fizz-buzz/")
        String url,

        @Schema(description = "The platform name the problem is on", example = "Leetcode")
        String platformName,

        @Schema(description = "The difficulty level of the problem", example = "Easy")
        String difficulty
) {
}
