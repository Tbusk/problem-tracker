package github.io.tbusk.problem_tracker.accountservice.create.dtos;

public record CreateRequestDTO(
        String emailAddress,
        String password
) {
}
