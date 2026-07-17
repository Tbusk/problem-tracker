package github.io.tbusk.problem_tracker.problemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the problem service, which manages competitive programming problem data
 * including problems, categories, difficulties, platforms, and user-problem tracking.
 */
@SpringBootApplication
public class ProblemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProblemServiceApplication.class, args);
    }

}
