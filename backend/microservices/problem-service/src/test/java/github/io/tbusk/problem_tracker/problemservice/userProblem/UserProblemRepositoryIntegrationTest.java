package github.io.tbusk.problem_tracker.problemservice.userProblem;

import github.io.tbusk.problem_tracker.problemservice.environment.Environment;
import github.io.tbusk.problem_tracker.problemservice.environment.EnvironmentRepository;
import github.io.tbusk.problem_tracker.problemservice.problem.database.Problem;
import github.io.tbusk.problem_tracker.problemservice.problem.database.ProblemRepository;
import github.io.tbusk.problem_tracker.problemservice.programmingLanguage.ProgrammingLanguage;
import github.io.tbusk.problem_tracker.problemservice.programmingLanguage.ProgrammingLanguageRepository;
import github.io.tbusk.problem_tracker.problemservice.user.User;
import github.io.tbusk.problem_tracker.problemservice.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class UserProblemRepositoryIntegrationTest {

    @Autowired
    private UserProblemRepository userProblemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Test
    void shouldSaveUserProblem() {
        String emailAddress = "test.user@test.com";
        String problemName = "Two Sum";
        String platformName = "Leetcode";
        String programmingLanguageName = "Java";
        String environmentName = "Whiteboard";
        Float minutes = 15.5f;

        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        Optional<Problem> problem = problemRepository.findByNameAndPlatform(problemName, platformName);
        Optional<ProgrammingLanguage> programmingLanguage = programmingLanguageRepository.findByName(programmingLanguageName);
        Optional<Environment> environment = environmentRepository.findByName(environmentName);

        assertTrue(user.isPresent());
        assertTrue(problem.isPresent());
        assertTrue(programmingLanguage.isPresent());
        assertTrue(environment.isPresent());

        UserProblem userProblem = new UserProblem(programmingLanguage.get(), minutes, problem.get(), user.get(), environment.get());

        UserProblem saved = userProblemRepository.save(userProblem);

        assertNotNull(saved.getId());
        assertEquals(emailAddress, saved.getUser().getEmailAddress());
        assertEquals(problemName, saved.getProblem().getName());
        assertEquals(platformName, saved.getProblem().getPlatform().getName());
        assertEquals(programmingLanguageName, saved.getProgrammingLanguage().getName());
        assertEquals(minutes, saved.getMinutes());
        assertNotNull(saved.getSolvedOn());
        assertEquals(environmentName, saved.getEnvironment().getName());
    }

    @Test
    void shouldUpdateUserProblem() {
        String emailAddress = "test.user@test.com";
        String problemName = "Two Sum";
        String platformName = "Leetcode";
        String environmentName = "Whiteboard";
        String originalProgrammingLanguageName = "Java";
        String updatedProgrammingLanguageName = "Python3";
        Float originalMinutes = 15.5f;
        Float updatedMinutes = 10.0f;

        Optional<User> user = userRepository.findByEmailAddress(emailAddress);
        Optional<Problem> problem = problemRepository.findByNameAndPlatform(problemName, platformName);
        Optional<ProgrammingLanguage> originalProgrammingLanguage = programmingLanguageRepository.findByName(originalProgrammingLanguageName);
        Optional<ProgrammingLanguage> updatedProgrammingLanguage = programmingLanguageRepository.findByName(updatedProgrammingLanguageName);
        Optional<Environment> environment = environmentRepository.findByName(environmentName);

        assertTrue(user.isPresent());
        assertTrue(problem.isPresent());
        assertTrue(originalProgrammingLanguage.isPresent());
        assertTrue(updatedProgrammingLanguage.isPresent());
        assertTrue(environment.isPresent());

        UserProblem userProblem = new UserProblem(originalProgrammingLanguage.get(), originalMinutes, problem.get(), user.get(), environment.get());

        UserProblem saved = userProblemRepository.save(userProblem);
        Long savedId = saved.getId();

        saved.setMinutes(updatedMinutes);
        saved.setProgrammingLanguage(updatedProgrammingLanguage.get());

        UserProblem updated = userProblemRepository.save(saved);

        assertEquals(savedId, updated.getId());
        assertEquals(updatedMinutes, updated.getMinutes());
        assertEquals(updatedProgrammingLanguageName, updated.getProgrammingLanguage().getName());
        assertEquals(emailAddress, updated.getUser().getEmailAddress());
        assertEquals(problemName, updated.getProblem().getName());
        assertEquals(environmentName, updated.getEnvironment().getName());
    }
}
