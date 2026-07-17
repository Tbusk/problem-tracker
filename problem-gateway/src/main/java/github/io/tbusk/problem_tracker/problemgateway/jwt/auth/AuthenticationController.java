package github.io.tbusk.problem_tracker.problemgateway.jwt.auth;

import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.CreateJwtRequest;
import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.JwtToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes authentication endpoints for creating JWT tokens.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    /**
     * @param authenticationService service responsible for authenticating users and issuing tokens
     */
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param request the credentials containing email and password
     * @return the JWT token
     * @throws AuthenticationException if the credentials are invalid
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public JwtToken createToken(@RequestBody CreateJwtRequest request) throws AuthenticationException {
        return authenticationService.authenticate(request);
    }
}
