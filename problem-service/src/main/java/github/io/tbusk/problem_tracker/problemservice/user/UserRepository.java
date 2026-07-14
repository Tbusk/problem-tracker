package github.io.tbusk.problem_tracker.problemservice.user;

import github.io.tbusk.problem_tracker.problemservice.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//TODO: add javadoc for UserRepository
@Repository
public interface UserRepository extends PagingAndSortingRepository <User, Long> {

    User save(User user);

}
