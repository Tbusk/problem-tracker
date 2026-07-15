package github.io.tbusk.problem_tracker.problemservice.difficulty.exceptions;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions related to difficulties.
 */
@RestControllerAdvice
public class DifficultyExceptionHandler {

    /**
     * Handles exceptions related to difficulties not being found.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DifficultyNotFoundException.class)
    private ErrorResponseDTO handleDifficultyNotFoundException(DifficultyNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
