package github.io.tbusk.problem_tracker.accountservice.create.exceptions;

import github.io.tbusk.problem_tracker.accountservice.exceptions.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * REST controller advice that handles exceptions thrown by the account creation flow.
 */
@RestControllerAdvice
public class CreateExceptionHandler {

    /**
     * Handles {@link InvalidPasswordException} by returning a {@code 400 Bad Request} response
     * containing the validation message.
     *
     * @param ex the thrown invalid password exception
     * @return a dto containing the error message
     */
    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleInvalidPasswordException(InvalidPasswordException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }

    /**
     * Handles {@link EmailAddressInUseException} by returning a {@code 409 Conflict} response
     * containing the validation message.
     *
     * @param ex the thrown email address in use exception
     * @return a dto containing the error message
     */
    @ExceptionHandler(EmailAddressInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleEmailAddressInUseException(EmailAddressInUseException ex) {
        return new ErrorResponseDTO(ex.getMessage());
    }
}
