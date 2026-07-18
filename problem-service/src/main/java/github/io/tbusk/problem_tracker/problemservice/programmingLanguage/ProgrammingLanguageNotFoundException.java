package github.io.tbusk.problem_tracker.problemservice.programmingLanguage;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a requested programming language is not found in the database, e.g., "D".
 */
public class ProgrammingLanguageNotFoundException extends ProblemServiceException {

    /**
     * Creates an exception with a message indicating the programming language was not found
     */
    public ProgrammingLanguageNotFoundException() {
        super("Cannot find a programming language with that name. Please check your spelling and that the language is supported.");
    }
}
