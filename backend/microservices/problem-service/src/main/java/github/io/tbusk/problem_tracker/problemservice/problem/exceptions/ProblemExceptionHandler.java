package github.io.tbusk.problem_tracker.problemservice.problem.exceptions;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions thrown related to problems, such as validation errors and already existing problems.
 */
@RestControllerAdvice
public class ProblemExceptionHandler {

    /**
     * Handles exceptions related to problem already existing in the system.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProblemAlreadyExistsException.class)
    private ErrorResponseDTO handleProblemAlreadyExistsException(ProblemAlreadyExistsException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles exceptions related to the problem name not being valid.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProblemNameValidationException.class)
    private ErrorResponseDTO handleProblemNameValidationException(ProblemNameValidationException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles exceptions related to the problem Url not being valid.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProblemUrlValidationException.class)
    private ErrorResponseDTO handleProblemUrlValidationException(ProblemUrlValidationException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles exceptions related to the problem not being found.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProblemNotFoundException.class)
    private ErrorResponseDTO handleProblemNotFoundException(ProblemNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
