package github.io.tbusk.problem_tracker.problemservice.problemCategory;

import github.io.tbusk.problem_tracker.problemservice.problemCategory.ProblemCategory;
import github.io.tbusk.problem_tracker.problemservice.problemCategory.ProblemCategoryID;
import org.springframework.data.repository.Repository;

import java.util.Collection;

//TODO: add javadoc for ProblemCategoryRepository
@org.springframework.stereotype.Repository
public interface ProblemCategoryRepository extends Repository<ProblemCategory, ProblemCategoryID> {

    ProblemCategory save(ProblemCategory problemCategory);

    Collection<ProblemCategory> findAllByProblem_Id(Integer id);

}
