package github.io.tbusk.problem_tracker.problemservice.problem.exceptions;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

//TODO: add javadoc for ProbelmUrlValidationException
public class ProblemUrlValidationException extends ProblemServiceException {
    public ProblemUrlValidationException(String url) {
        super(String.format("'%s' is not a valid url", url));
    }
}
