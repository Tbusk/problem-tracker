package github.io.tbusk.problem_tracker.problemgateway.security;


import github.io.tbusk.problem_tracker.problemgateway.jwt.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtRequestFilter implements WebFilter {

    private JwtService jwtService;

    public JwtRequestFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = getToken(exchange.getRequest());

        if (token != null && jwtService.validateToken(token)) {
            Claims claims = jwtService.getClaims(token);
            String emailAddress = claims.getSubject();

            if (emailAddress == null) {
                return null;
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(emailAddress, null, List.of());

            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        }

        return chain.filter(exchange);
    }

    public String getToken(ServerHttpRequest request) {
        String authenticationHeader = request.getHeaders().getFirst("Authorization");

        String bearerPrefix = "Bearer ";

        if (authenticationHeader != null && authenticationHeader.startsWith(bearerPrefix)) {
            return authenticationHeader.substring(bearerPrefix.length());
        }

        return null;
    }
}
