package github.io.tbusk.problem_tracker.problemgateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/**
 * Configures Spring Security for the API gateway.
 * Disables CSRF, basic auth, and form login, adds JWT authentication filtering, and permits
 * unauthenticated access to authentication and account creation endpoints.
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
                    auth.pathMatchers(HttpMethod.POST, "/api/v1/auth").permitAll();
                    auth.pathMatchers(HttpMethod.POST, "/account-service/api/v1/create-account").permitAll();
                    auth.anyExchange().authenticated();
                })
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build();
    }
}
