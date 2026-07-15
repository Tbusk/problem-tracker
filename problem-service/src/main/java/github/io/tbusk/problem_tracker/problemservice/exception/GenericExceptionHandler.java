package github.io.tbusk.problem_tracker.problemservice.exception;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.URISyntaxException;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    private ErrorResponseDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponseDTO("Invalid argument: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    private ErrorResponseDTO handleIllegalArgumentException(NoResourceFoundException ex) {
        return new ErrorResponseDTO("Please check your url again. The resource you are looking for does not exist.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageSizeOutsideBoundsException.class)
    private ErrorResponseDTO handlePageSizeOutsideBoundsException(PageSizeOutsideBoundsException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageTooSmallException.class)
    private ErrorResponseDTO handlePageTooSmallException(PageTooSmallException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(URISyntaxException.class)
    private ErrorResponseDTO handleURISyntaxException(URISyntaxException ex) {
        return new ErrorResponseDTO("The url you supplied is not valid. Please check your url, and try again.");
    }
}
