package github.io.tbusk.problem_tracker.problemservice.problem.database;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//TODO: add javadoc for ProblemRepository
@Repository
public interface ProblemRepository extends PagingAndSortingRepository<Problem, Integer> {

    Problem save(Problem problem);
}
