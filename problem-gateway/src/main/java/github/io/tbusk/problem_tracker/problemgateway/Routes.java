package github.io.tbusk.problem_tracker.problemgateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines the API gateway routes, mapping incoming request paths to downstream microservices
 * via load-balanced URIs registered in service discovery.
 */
@Configuration
public class Routes {

    /**
     * Configures the route locator with paths for the existing microservices, such as the problem service and account
     * service.
     *
     * @param builder the route locator builder
     * @return the configured route locator
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("problem_service",
                        r -> r
                                .path("/problem-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://problem-service")
                )
                .route("account_service",
                        r -> r
                                .path("/account-service/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://account-service")
                )
                .build();
    }

}
