package github.io.tbusk.problem_tracker.problemservice.exception;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.URISyntaxException;

/**
 * Handles exceptions related to general application issues, such as bad requests, invalid URIs, and
 * pagination errors.
 */
@RestControllerAdvice
public class GenericExceptionHandler {

    /**
     * Handles exceptions related to an illegal argument being passed to the application.
     *
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    private ErrorResponseDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponseDTO("Invalid argument: " + ex.getMessage());
    }

    /**
     * Handles exceptions related to a resource not being found.
     *
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    private ErrorResponseDTO handleNoResourceFoundException(NoResourceFoundException ex) {
        return new ErrorResponseDTO("Please check your url again. The resource you are looking for does not exist.");
    }

    /**
     * Handles exceptions related to the requested page size being outside valid bounds.
     *
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageSizeOutsideBoundsException.class)
    private ErrorResponseDTO handlePageSizeOutsideBoundsException(PageSizeOutsideBoundsException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles exceptions related to the requested page number being too small.
     *
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageTooSmallException.class)
    private ErrorResponseDTO handlePageTooSmallException(PageTooSmallException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles exceptions related to an invalid URI being provided.
     *
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(URISyntaxException.class)
    private ErrorResponseDTO handleURISyntaxException(URISyntaxException ex) {
        return new ErrorResponseDTO("The url you supplied is not valid. Please check your url, and try again.");
    }
}
