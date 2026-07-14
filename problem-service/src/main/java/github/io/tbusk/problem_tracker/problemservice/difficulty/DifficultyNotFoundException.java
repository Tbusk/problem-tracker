package github.io.tbusk.problem_tracker.problemservice.difficulty;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

//TODO: add javadoc for DifficultyNotFoundException
public class DifficultyNotFoundException extends ProblemServiceException {

    public DifficultyNotFoundException(String difficulty) {
        super(String.format("'%s' is not a valid difficulty level.", difficulty));
    }
}
