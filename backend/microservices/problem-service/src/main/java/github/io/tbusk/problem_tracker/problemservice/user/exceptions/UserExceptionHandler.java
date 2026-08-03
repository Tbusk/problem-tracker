package github.io.tbusk.problem_tracker.problemservice.user.exceptions;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions related to users.
 */
@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * Handles exceptions related to users not being found.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    private ErrorResponseDTO handleUserNotFoundException(UserNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
