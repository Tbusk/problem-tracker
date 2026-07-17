package github.io.tbusk.problem_tracker.problemgateway.security;


import github.io.tbusk.problem_tracker.problemgateway.jwt.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Reactive filter that intercepts each incoming web request, extracts the JWT token from the Authorization
 * header, validates it, and sets the reactive security context if the token is valid.
 */
@Component
public class JwtRequestFilter implements WebFilter {

    private JwtService jwtService;

    /**
     * @param jwtService the service used to validate tokens and extract claims
     */
    public JwtRequestFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Extracts and validates the JWT token from the request, then populates the reactive security context.
     *
     * @param exchange the server web exchange containing the request and response
     * @param chain    the web filter chain to continue processing
     * @return a Mono that completes when filtering is done
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = getToken(exchange.getRequest());

        if (token != null && jwtService.validateToken(token)) {
            Claims claims = jwtService.getClaims(token);

            if (claims == null) {
                return chain.filter(exchange);
            }

            String emailAddress = claims.getSubject();

            if (emailAddress == null) {
                return chain.filter(exchange);
            }

            SimpleGrantedAuthority role = getRole(claims);

            List<SimpleGrantedAuthority> authorities = role != null ? List.of(role) : List.of();

            Authentication authentication = new UsernamePasswordAuthenticationToken(emailAddress, null, authorities);

            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }

        return chain.filter(exchange);
    }

    /**
     * Extracts the JWT token from the Authorization header, stripping the Bearer prefix.
     *
     * @param request the server HTTP request
     * @return the JWT token, or null if no valid Authorization header is present
     */
    public String getToken(ServerHttpRequest request) {
        String authenticationHeader = request.getHeaders().getFirst("Authorization");

        String bearerPrefix = "Bearer ";

        if (authenticationHeader != null && authenticationHeader.startsWith(bearerPrefix)) {
            return authenticationHeader.substring(bearerPrefix.length());
        }

        return null;
    }

    /**
     * Extracts the role claim from the JWT claims and converts it to a {@link SimpleGrantedAuthority}.
     *
     * @param claims the JWT claims
     * @return the granted authority with role prefix, or null if no role is present
     */
    private SimpleGrantedAuthority getRole(Claims claims) {
        String role = claims.get("role", String.class);
        String rolePrefix = "ROLE_";

        if (role == null) {
            return null;
        }

        return new SimpleGrantedAuthority(rolePrefix + role);
    }
}
