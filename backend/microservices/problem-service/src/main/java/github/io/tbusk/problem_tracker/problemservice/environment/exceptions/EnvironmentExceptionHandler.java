package github.io.tbusk.problem_tracker.problemservice.environment.exceptions;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions related to the environment.
 */
@RestControllerAdvice
public class EnvironmentExceptionHandler {

    /**
     * Handles exceptions related to the environment not being found.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EnvironmentNotFoundException.class)
    private ErrorResponseDTO handleEnvironmentNotFoundException(EnvironmentNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
