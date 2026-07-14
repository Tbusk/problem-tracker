package github.io.tbusk.problem_tracker.problemservice.platform;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;

//TODO: add javadoc for PlatformNotFoundExeption
public class PlatformNotFoundException extends ProblemServiceException {
    public PlatformNotFoundException(String platform) {
        super(String.format("'%s' is not a valid platform.", platform));
    }
}
