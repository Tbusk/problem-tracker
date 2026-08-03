package github.io.tbusk.problem_tracker.problemgateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/**
 * Configures Spring Security for the API gateway.
 * Disables CSRF, basic auth, and form login, adds JWT authentication filtering, and permits
 * unauthenticated access to authentication and account creation endpoints. Every endpoint has an
 * explicit authorization rule, and any request without a matching rule is denied by default.
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * @param jwtRequestFilter the JWT filter that validates tokens on each request
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configures the reactive security filter chain with JWT-based stateless authentication.
     *
     * @param http the HTTP security configuration
     * @return the configured security web filter chain
     */
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterAt(jwtRequestFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(auth -> {
                    auth.pathMatchers(HttpMethod.POST, "/authentication-service/api/v1/auth").permitAll();
                    auth.pathMatchers(HttpMethod.POST, "/account-service/api/v1/create-account").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/authentication-service/actuator/health").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/account-service/actuator/health").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/problem-service/actuator/health").permitAll();
                    auth.pathMatchers(HttpMethod.GET, "/actuator/health").permitAll();
                    auth.pathMatchers(HttpMethod.POST, "/problem-service/api/v1/problem").authenticated();
                    auth.pathMatchers(HttpMethod.GET, "/problem-service/api/v1/problem/all").authenticated();
                    auth.pathMatchers(HttpMethod.POST, "/problem-service/api/v1/user-problem").authenticated();
                    auth.pathMatchers(HttpMethod.GET, "/reporting-service/actuator/health").permitAll();
                    auth.anyExchange().denyAll();
                })
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build();
    }
}
