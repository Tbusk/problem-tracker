package github.io.tbusk.problem_tracker.accountservice.create.exceptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
