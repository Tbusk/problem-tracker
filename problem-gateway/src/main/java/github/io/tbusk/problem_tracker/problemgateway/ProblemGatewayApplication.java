package github.io.tbusk.problem_tracker.problemgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProblemGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProblemGatewayApplication.class, args);
    }

}
