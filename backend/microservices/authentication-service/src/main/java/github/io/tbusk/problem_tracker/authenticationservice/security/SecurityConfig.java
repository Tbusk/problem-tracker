package github.io.tbusk.problem_tracker.authenticationservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures Spring Security for the authentication service.
 * Disables CSRF, basic auth, and form login, uses stateless sessions, and permits
 * unauthenticated access to the token creation and health endpoints. Every endpoint has an
 * explicit authorization rule, and any request without a matching rule is denied by default.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain with stateless authentication.
     *
     * @param http the HTTP security configuration
     * @return the configured security filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/actuator/health").permitAll();
                    auth.anyRequest().denyAll();
                })
                .build();
    }
}
