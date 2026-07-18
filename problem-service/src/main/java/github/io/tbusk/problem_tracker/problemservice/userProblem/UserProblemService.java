package github.io.tbusk.problem_tracker.problemservice.userProblem;

import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.exceptions.ProblemNotFoundException;
import github.io.tbusk.problem_tracker.problemservice.problem.sanitizers.ProblemSanitizer;
import github.io.tbusk.problem_tracker.problemservice.programmingLanguage.ProgrammingLanguage;
import github.io.tbusk.problem_tracker.problemservice.programmingLanguage.ProgrammingLanguageNotFoundException;
import github.io.tbusk.problem_tracker.problemservice.programmingLanguage.ProgrammingLanguageRepository;
import github.io.tbusk.problem_tracker.problemservice.response.SuccessResponseDTO;
import github.io.tbusk.problem_tracker.problemservice.user.User;
import github.io.tbusk.problem_tracker.problemservice.user.UserNotFoundException;
import github.io.tbusk.problem_tracker.problemservice.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for tracking when users solve competitive programming problems, including the
 * programming language used and time taken.
 */
@Service
public class UserProblemService {

    /**
     * Minimum minutes allowed for a user problem.
     */
    public static final float MINUTES_MINIMUM = 0.1f;

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final ProgrammingLanguageRepository programmingLanguageRepository;
    private final UserProblemRepository userProblemRepository;

    /**
     * Creates a service instance with the required repositories
     *
     * @param userRepository                repository for resolving users
     * @param problemRepository             repository for resolving problems
     * @param programmingLanguageRepository repository for resolving programming languages
     * @param userProblemRepository         repository for persisting user problem records
     */
    public UserProblemService(UserRepository userRepository, ProblemRepository problemRepository, ProgrammingLanguageRepository programmingLanguageRepository, UserProblemRepository userProblemRepository) {
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
        this.programmingLanguageRepository = programmingLanguageRepository;
        this.userProblemRepository = userProblemRepository;
    }

    /**
     * Records that a user solved a competitive programming problem, validating the input, resolving the
     * user, problem, and programming language, and persisting the record to the database.
     *
     * @param emailAddress the email address of the user
     * @param request      dto containing the problem name, platform, programming language, and minutes taken
     * @return a success response indicating the problem was recorded
     * @throws ProblemServiceException  if the user, problem, or programming language is not found
     * @throws IllegalArgumentException if a required argument is null or minutes is below {@value #MINUTES_MINIMUM}
     */
    public SuccessResponseDTO add(String emailAddress, AddUserProblemDTO request) throws ProblemServiceException {

        if (emailAddress == null) {
            throw new IllegalArgumentException("Email address cannot be empty");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request cannot be empty");
        }

        if (Float.compare(MINUTES_MINIMUM, request.minutes()) > 0) {
            throw new IllegalArgumentException("Minutes cannot be less than " + MINUTES_MINIMUM);
        }

        if (request.platformName() == null) {
            throw new IllegalArgumentException("Platform name cannot be empty");
        }

        if (request.problemName() == null) {
            throw new IllegalArgumentException("Problem name cannot be empty");
        }

        if (request.programmingLanguage() == null) {
            throw new IllegalArgumentException("Programming language cannot be empty");
        }

        Optional<User> user = userRepository.findByEmailAddress(emailAddress);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        Optional<Problem> problem = problemRepository.findByNameAndPlatform(
                ProblemSanitizer.sanitizeName(request.problemName()),
                ProblemSanitizer.sanitizeName(request.platformName())
        );

        if (problem.isEmpty()) {
            throw new ProblemNotFoundException();
        }


        Optional<ProgrammingLanguage> programmingLanguage = programmingLanguageRepository.findByName(
                ProblemSanitizer.sanitizeName(request.programmingLanguage())
        );

        if (programmingLanguage.isEmpty()) {
            throw new ProgrammingLanguageNotFoundException();
        }

        UserProblem userProblem = new UserProblem(
                programmingLanguage.get(),
                request.minutes(),
                problem.get(),
                user.get()
        );

        userProblemRepository.save(userProblem);

        return new SuccessResponseDTO("Problem recorded successfully!");
    }
}
