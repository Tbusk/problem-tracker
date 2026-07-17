package github.io.tbusk.problem_tracker.problemservice.security;

import github.io.tbusk.problem_tracker.problemservice.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);

        if (token != null && jwtService.validateToken(token)) {
            Claims claims = jwtService.getClaims(token);

            if (claims == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String emailAddress = claims.getSubject();

            if (emailAddress == null) {
                filterChain.doFilter(request, response);
                return;
            }

            SimpleGrantedAuthority role = getRole(claims);

            List<SimpleGrantedAuthority> authorities = role != null ? List.of(role) : List.of();

            Authentication authentication = new UsernamePasswordAuthenticationToken(emailAddress, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String bearerPrefix = "Bearer ";

        if (authHeader != null && authHeader.startsWith(bearerPrefix)) {
            return authHeader.substring(bearerPrefix.length());
        }

        return null;
    }

    private SimpleGrantedAuthority getRole(Claims claims) {
        String role = claims.get("role", String.class);
        String rolePrefix = "ROLE_";

        if (role == null) {
            return null;
        }

        return new SimpleGrantedAuthority(rolePrefix + role);
    }
}
