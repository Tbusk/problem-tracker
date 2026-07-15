package github.io.tbusk.problem_tracker.problemservice.platform.exceptions;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions related to platforms.
 */
@RestControllerAdvice
public class PlatformExceptionHandler {

    /**
     * Handles exceptions related to platforms not being found.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PlatformNotFoundException.class)
    private ErrorResponseDTO handlePlatformNotFoundException(PlatformNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
