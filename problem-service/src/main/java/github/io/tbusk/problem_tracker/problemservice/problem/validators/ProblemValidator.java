package github.io.tbusk.problem_tracker.problemservice.problem.validators;

//TODO: add javadoc for ProblemValidator, constants, validateName(), and validateUrl()
public class ProblemValidator {

    public static final int NAME_MINIMUM_LENGTH = 2;
    public static final int NAME_MAXIMUM_LENGTH = 128;
    public static final int URL_MINIMUM_LENGTH = 10;
    public static final int URL_MAXIMUM_LENGTH = 512;

    //TODO: add tests for validateName()
    public static boolean validateName(String name) {
        if (name == null) {
            return false;
        }

        if (name.trim().length() < NAME_MINIMUM_LENGTH) {
            return false;
        }

        if (name.trim().length() > NAME_MAXIMUM_LENGTH) {
            return false;
        }

        return true;
    }

    //TODO: add tests for validateUrl()
    public static boolean validateUrl(String url) {
        if (url == null) {
            return false;
        }

        if (url.trim().length() < URL_MINIMUM_LENGTH) {
            return false;
        }

        if (url.trim().length() > URL_MAXIMUM_LENGTH) {
            return false;
        }

        return true;
    }
}
