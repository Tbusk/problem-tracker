package github.io.tbusk.problem_tracker.problemservice.problem.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

//TODO: add javadoc for ProbelmNameValidationException
public class ProblemNameValidationException extends ProblemServiceException {
    public ProblemNameValidationException(String name) {
        super(String.format("'%s' is not a valid name.", name));
    }
}
