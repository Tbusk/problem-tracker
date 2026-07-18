package github.io.tbusk.problem_tracker.accountservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures Spring Security for the account service.
 * Disables basic auth, form login, and CSRF, enforces stateless sessions, permits unauthenticated
 * access to account creation, and adds JWT authentication filtering for all other requests.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * @param jwtAuthenticationFilter the JWT filter that validates tokens on each request
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain with JWT-based stateless authentication.
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
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/create-account").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/actuator/health").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }
}
