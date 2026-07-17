package github.io.tbusk.problem_tracker.problemgateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routes {

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
