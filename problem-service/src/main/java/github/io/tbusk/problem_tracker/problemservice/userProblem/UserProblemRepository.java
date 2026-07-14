package github.io.tbusk.problem_tracker.problemservice.userProblem;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

//TODO: add javadoc for UserProblemRepository
@Repository
public interface UserProblemRepository extends PagingAndSortingRepository<UserProblem, Long> {

    Collection<UserProblem> findAll();

}
