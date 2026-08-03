package github.io.tbusk.problem_tracker.problemservice.programmingLanguage.exceptions;

import github.io.tbusk.problem_tracker.problemservice.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles exceptions related to programming language operations.
 */
@RestControllerAdvice
public class ProgrammingLanguageExceptionHandler {

    /**
     * Handles exceptions related to the programming language not being found.
     * @param ex the exception
     * @return error response DTO
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProgrammingLanguageNotFoundException.class)
    private ErrorResponseDTO handleProgrammingLanguageNotFoundException(ProgrammingLanguageNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
