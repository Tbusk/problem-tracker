package github.io.tbusk.problem_tracker.problemgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Entry point for the API gateway, which routes requests to downstream microservices and handles
 * JWT-based authentication.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProblemGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProblemGatewayApplication.class, args);
    }

}
