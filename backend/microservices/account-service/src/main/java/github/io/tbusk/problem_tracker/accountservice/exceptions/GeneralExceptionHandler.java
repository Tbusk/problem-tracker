package github.io.tbusk.problem_tracker.accountservice.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * General REST controller advice that handles general exceptions thrown throughout the application.
 */
@RestControllerAdvice
public class GeneralExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GeneralExceptionHandler.class);

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

    /**
     * Handles {@link NoResourceFoundException} by returning a {@code 404 Not Found} response.
     *
     * @param ex the thrown no resource found exception
     * @return a dto containing the error message
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleNoResourceFoundException(NoResourceFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles {@link ConstraintViolationException} by returning a {@code 400 Bad Request} response.
     * Typically thrown when a database constraint (e.g. unique key, foreign key) is violated.
     *
     * @param ex the thrown constraint violation exception
     * @return a dto containing the error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return new ErrorResponseDTO("The provided data conflicts with an existing record. Please verify your input and try again.");
    }

    /**
     * Handles {@link InvalidEmailException} by returning a {@code 400 Bad Request} response.
     * Thrown when the supplied email address is not in a valid format.
     *
     * @param ex the thrown invalid email exception
     * @return a dto containing the error message
     */
    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleInvalidEmailException(InvalidEmailException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
