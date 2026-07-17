package github.io.tbusk.problem_tracker.problemgateway.jwt.auth;

import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.CreateJwtRequest;
import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.JwtToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public JwtToken createToken(@RequestBody CreateJwtRequest request) throws AuthenticationException {
        return authenticationService.authenticate(request);
    }
}
