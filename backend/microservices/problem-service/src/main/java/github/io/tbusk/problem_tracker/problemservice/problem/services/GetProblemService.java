package github.io.tbusk.problem_tracker.problemservice.problem.services;

import github.io.tbusk.problem_tracker.problemservice.exception.PageSizeOutsideBoundsException;
import github.io.tbusk.problem_tracker.problemservice.exception.PageTooSmallException;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.dtos.GetProblemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for retrieving and paginating competitive programming problems.
 */
@Service
public class GetProblemService {

    /** The default page size when none is provided. */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /** The maximum allowed page size. */
    public static final int MAX_PAGE_SIZE = 100;
    /** The minimum allowed page size. */
    public static final int MIN_PAGE_SIZE = 1;

    private ProblemRepository problemRepository;

    /**
     * Creates a service instance with the required repository.
     *
     * @param problemRepository repository for retrieving problems
     */
    public GetProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    /**
     * Retrieves a paginated list of all problems.
     * Defaults the offset to 0 and page size to {@value #DEFAULT_PAGE_SIZE} if not provided.
     * Validates that offset is non-negative and page size is within bounds.
     *
     * @param offset   the page index (zero-based), defaults to 0
     * @param pageSize the number of problems per page, must be between {@value #MIN_PAGE_SIZE}
     *                 and {@value #MAX_PAGE_SIZE}
     * @return a list of {@link GetProblemDTO} objects for the requested page
     * @throws PageSizeOutsideBoundsException if page size exceeds allowed range
     * @throws PageTooSmallException          if the offset is negative
     */
    public List<GetProblemDTO> getAll(Integer offset, Integer pageSize) throws PageSizeOutsideBoundsException, PageTooSmallException {

        if (offset == null) {
            offset = 0;
        }

        if (offset < 0) {
            throw new PageTooSmallException();
        }

        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (pageSize > MAX_PAGE_SIZE || pageSize < MIN_PAGE_SIZE) {
            throw new PageSizeOutsideBoundsException(MAX_PAGE_SIZE, MIN_PAGE_SIZE);
        }

        Page<Problem> problemsPage = problemRepository.findAll(PageRequest.of(offset, pageSize));

        List<Problem> problems = problemsPage.getContent();

        List<GetProblemDTO> problemDTOs = new ArrayList<>(problems.size());

        for (Problem problem : problems) {
            GetProblemDTO problemDTO = convertProblemToGetProblemDTO(problem);

            problemDTOs.add(problemDTO);
        }

        return problemDTOs;
    }

    /**
     * Converts a {@link Problem} entity to a {@link GetProblemDTO}.
     *
     * @param problem the problem entity to convert
     * @return the corresponding DTO
     */
    private GetProblemDTO convertProblemToGetProblemDTO(Problem problem) {
        return new GetProblemDTO(
                problem.getName(),
                problem.getUrl(),
                problem.getPlatform().getName(),
                problem.getDifficulty().getName()
        );
    }
}
