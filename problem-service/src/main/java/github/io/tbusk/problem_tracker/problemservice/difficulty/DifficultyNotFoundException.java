package github.io.tbusk.problem_tracker.problemservice.difficulty;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

/**
 * Thrown when a requested difficulty level is not found in the database, e.g., "Impossible".
 */
public class DifficultyNotFoundException extends ProblemServiceException {

    /**
     * Creates an exception with a message indicating the difficulty is not valid
     *
     * @param difficulty the invalid difficulty name provided
     */
    public DifficultyNotFoundException(String difficulty) {
        super(String.format("'%s' is not a valid difficulty level.", difficulty));
    }
}
