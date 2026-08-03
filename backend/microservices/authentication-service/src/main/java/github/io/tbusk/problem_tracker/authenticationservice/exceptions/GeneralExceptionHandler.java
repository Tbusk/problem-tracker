package github.io.tbusk.problem_tracker.authenticationservice.exceptions;

import github.io.tbusk.problem_tracker.authenticationservice.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * General REST controller advice that handles exceptions thrown throughout the application.
 */
@RestControllerAdvice
public class GeneralExceptionHandler {

    /**
     * Handles {@link AuthenticationException} by returning a {@code 401 Unauthorized} response.
     * Thrown when the supplied credentials are invalid or the account is locked or disabled.
     *
     * @param ex the thrown authentication exception
     * @return a dto containing the error message
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handleAuthenticationException(AuthenticationException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles {@link IllegalArgumentException} by returning a {@code 400 Bad Request} response.
     *
     * @param ex the thrown illegal argument exception
     * @return a dto containing the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponseDTO("Invalid argument: " + ex.getMessage());
    }
}
