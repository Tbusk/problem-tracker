package github.io.tbusk.problem_tracker.problemservice.problem.services;

import github.io.tbusk.problem_tracker.problemservice.difficulty.Difficulty;
import github.io.tbusk.problem_tracker.problemservice.difficulty.DifficultyRepository;
import github.io.tbusk.problem_tracker.problemservice.difficulty.exceptions.DifficultyNotFoundException;
import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.platform.Platform;
import github.io.tbusk.problem_tracker.problemservice.platform.PlatformRepository;
import github.io.tbusk.problem_tracker.problemservice.platform.exceptions.PlatformNotFoundException;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.CreateProblemDTO;
import github.io.tbusk.problem_tracker.problemservice.problem.exceptions.ProblemAlreadyExistsException;
import github.io.tbusk.problem_tracker.problemservice.problem.exceptions.ProblemNameValidationException;
import github.io.tbusk.problem_tracker.problemservice.problem.exceptions.ProblemUrlValidationException;
import github.io.tbusk.problem_tracker.problemservice.problem.sanitizers.ProblemSanitizer;
import github.io.tbusk.problem_tracker.problemservice.problem.validators.ProblemValidator;
import github.io.tbusk.problem_tracker.problemservice.problemCategory.ProblemCategoryService;
import github.io.tbusk.problem_tracker.problemservice.response.SuccessResponseDTO;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Service responsible for creating and persisting new competitive programming problems.
 */
@Service
public class CreateProblemService {

    private ProblemRepository problemRepository;
    private DifficultyRepository difficultyRepository;
    private PlatformRepository platformRepository;
    private ProblemCategoryService problemCategoryService;

    /**
     * Creates a service instance with the required repositories
     *
     * @param problemRepository    repository for persisting problems
     * @param difficultyRepository repository for resolving difficulty levels
     * @param platformRepository   repository for resolving platforms
     * @param problemCategoryService service for managing problem categories
     */
    public CreateProblemService(ProblemRepository problemRepository, DifficultyRepository difficultyRepository, PlatformRepository platformRepository, ProblemCategoryService problemCategoryService) {
        this.problemRepository = problemRepository;
        this.difficultyRepository = difficultyRepository;
        this.platformRepository = platformRepository;
        this.problemCategoryService = problemCategoryService;
    }

    /**
     * Creates a new competitive programming problem from the given DTO.
     * Validates all input fields, sanitizes the name, resolves the difficulty and platform, verifies the problem
     * doesn't already exist in db, and persists the problem to the database.
     *
     * @param createRequest the DTO containing the problem's name, URL, difficulty, and platform
     * @return a success response indicating the problem was added
     * @throws ProblemServiceException  if the difficulty or platform is not found, if validation fails, or if it
     *                                  already exists in the database
     * @throws IllegalArgumentException if any required field in the DTO is null
     */
    public SuccessResponseDTO create(final CreateProblemDTO createRequest) throws ProblemServiceException, URISyntaxException {
        if (createRequest == null) {
            throw new IllegalArgumentException("CreateProblemDTO cannot be empty");
        }

        if (createRequest.difficulty() == null) {
            throw new IllegalArgumentException("Difficulty cannot be empty");
        }

        if (createRequest.platformName() == null) {
            throw new IllegalArgumentException("Platform cannot be empty");
        }

        if (createRequest.name() == null) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (createRequest.url() == null) {
            throw new IllegalArgumentException("Url cannot be empty");
        }

        if (createRequest.categories() == null) {
            throw new IllegalArgumentException("Categories cannot be empty");
        }

        if (createRequest.categories().isEmpty()) {
            throw new IllegalArgumentException("Please add at least one category");
        }

        Problem problem = new Problem();

        Optional<Difficulty> difficulty = difficultyRepository.findByName(createRequest.difficulty().trim());

        if (difficulty.isEmpty()) {
            throw new DifficultyNotFoundException(createRequest.difficulty());
        }

        problem.setDifficulty(difficulty.get());

        Optional<Platform> platform = platformRepository.findByName(createRequest.platformName().trim());

        if (!platform.isPresent()) {
            throw new PlatformNotFoundException();
        }

        problem.setPlatform(platform.get());

        if (!ProblemValidator.validateName(createRequest.name())) {
            throw new ProblemNameValidationException(createRequest.name());
        }

        String sanitizedName = ProblemSanitizer.sanitizeName(createRequest.name().trim());

        problem.setName(sanitizedName);

        if (!ProblemValidator.validateUrl(createRequest.url(), platform.get().getDomain())) {
            throw new ProblemUrlValidationException(createRequest.url());
        }

        String sanitizedUrl = ProblemSanitizer.sanitizeUrl(createRequest.url().trim());

        problem.setUrl(sanitizedUrl);

        Optional<Problem> existingProblem = problemRepository.findByDetails(
                sanitizedName,
                difficulty.get().getName(),
                platform.get().getName()
        );

        // do not want duplicates - which this aims to prevent that
        if (existingProblem.isPresent()) {
            throw new ProblemAlreadyExistsException();
        }

        problem = problemRepository.save(problem);

        // add categories
        problemCategoryService.createAll(problem, createRequest.categories());

        return new SuccessResponseDTO("Problem has been added successfully.");
    }
}
