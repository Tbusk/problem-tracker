package github.io.tbusk.problem_tracker.accountservice.security;

import github.io.tbusk.problem_tracker.accountservice.jwt.JwtService;
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
/**
 * Filter that intercepts each incoming HTTP request, extracts the JWT token from the Authorization header,
 * validates it, and sets the Spring Security authentication context if the token is valid.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /**
     * @param jwtService the service used to validate tokens and extract claims
     */
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Extracts and validates the JWT token from the request, then populates the security context.
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to continue processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

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

    /**
     * Extracts the JWT token from the Authorization header, stripping the Bearer prefix.
     *
     * @param request the HTTP request
     * @return the JWT token, or null if no valid Authorization header is present
     */
    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String bearerPrefix = "Bearer ";

        if (authHeader != null && authHeader.startsWith(bearerPrefix)) {
            return authHeader.substring(bearerPrefix.length());
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
