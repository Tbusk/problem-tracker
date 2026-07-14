package github.io.tbusk.problem_tracker.problemservice.problem.services;

import github.io.tbusk.problem_tracker.problemservice.difficulty.Difficulty;
import github.io.tbusk.problem_tracker.problemservice.difficulty.DifficultyNotFoundException;
import github.io.tbusk.problem_tracker.problemservice.difficulty.DifficultyRepository;
import github.io.tbusk.problem_tracker.problemservice.exception.ProblemServiceException;
import github.io.tbusk.problem_tracker.problemservice.platform.Platform;
import github.io.tbusk.problem_tracker.problemservice.platform.PlatformNotFoundException;
import github.io.tbusk.problem_tracker.problemservice.platform.PlatformRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.CreateProblemDTO;
import github.io.tbusk.problem_tracker.problemservice.problem.exceptions.ProblemNameValidationException;
import github.io.tbusk.problem_tracker.problemservice.problem.exceptions.ProblemUrlValidationException;
import github.io.tbusk.problem_tracker.problemservice.problem.sanitizers.ProblemSanitizer;
import github.io.tbusk.problem_tracker.problemservice.problem.validators.ProblemValidator;
import github.io.tbusk.problem_tracker.problemservice.response.SuccessResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

//TODO: add javadoc for CreateProblemService
@Service
public class CreateProblemService {

    private ProblemRepository problemRepository;
    private DifficultyRepository difficultyRepository;
    private PlatformRepository platformRepository;

    //TODO: add javadoc for constructor CreateProblemService()
    public CreateProblemService(ProblemRepository problemRepository, DifficultyRepository difficultyRepository, PlatformRepository platformRepository) {
        this.problemRepository = problemRepository;
        this.difficultyRepository = difficultyRepository;
        this.platformRepository = platformRepository;
    }

    //TODO: add javadoc for create()
    //TODO: add more tests for create()
    public SuccessResponseDTO create(final CreateProblemDTO createRequest) throws ProblemServiceException {
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

        Problem problem = new Problem();

        Optional<Difficulty> difficulty = difficultyRepository.findByName(createRequest.difficulty().trim());

        if (difficulty.isEmpty()) {
            throw new DifficultyNotFoundException(createRequest.difficulty());
        }

        problem.setDifficulty(difficulty.get());

        Optional<Platform> platform = platformRepository.findByName(createRequest.platformName().trim());

        if (platform.isEmpty()) {
            throw new PlatformNotFoundException(createRequest.platformName());
        }

        problem.setPlatform(platform.get());

        if (!ProblemValidator.validateName(createRequest.name())) {
            throw new ProblemNameValidationException(createRequest.name());
        }

        String sanitizedName = ProblemSanitizer.sanitizeName(createRequest.name().trim());

        problem.setName(sanitizedName);

        if (!ProblemValidator.validateUrl(createRequest.url())) {
            throw new ProblemUrlValidationException(createRequest.url());
        }

        // TODO: make sure record doesn't already exist in repo - don't want duplicates

        problem.setUrl(createRequest.url().trim());

        problemRepository.save(problem);

        return new SuccessResponseDTO("Problem has been added successfully.");
    }
}
