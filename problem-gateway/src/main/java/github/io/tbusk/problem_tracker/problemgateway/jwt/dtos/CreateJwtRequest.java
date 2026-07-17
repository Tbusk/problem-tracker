package github.io.tbusk.problem_tracker.problemgateway.jwt.dtos;

public record CreateJwtRequest(
        String emailAddress,
        String password
) {
}
