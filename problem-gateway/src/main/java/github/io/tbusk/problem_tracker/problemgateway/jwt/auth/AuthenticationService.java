package github.io.tbusk.problem_tracker.problemgateway.jwt.auth;

import github.io.tbusk.problem_tracker.problemgateway.jwt.JwtService;
import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.CreateJwtRequest;
import github.io.tbusk.problem_tracker.problemgateway.jwt.dtos.JwtToken;
import github.io.tbusk.problem_tracker.problemgateway.user.User;
import github.io.tbusk.problem_tracker.problemgateway.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public JwtToken authenticate(CreateJwtRequest createJwtRequest) throws AuthenticationException {

        if (createJwtRequest == null) {
            throw new IllegalArgumentException("The request body cannot be empty");
        }

        if (createJwtRequest.emailAddress() == null) {
            throw new IllegalArgumentException("The email address cannot be empty");
        }

        validateEmailAddress(createJwtRequest.emailAddress());

        Optional<User> potentialUser = userRepository.findByEmailAddress(createJwtRequest.emailAddress());

        if (!potentialUser.isPresent()) {
            throw new AuthenticationException("The email address or password is invalid");
        }

        User user = potentialUser.get();

        if (user.getLocked()) {
            throw new AuthenticationException("Your account is locked. Please contact support.");
        }

        if (!user.getEnabled()) {
            throw new AuthenticationException("Your account is disabled. Please contact support.");
        }

        if (createJwtRequest.password() == null) {
            throw new IllegalArgumentException("The password cannot be empty");
        }

        if (!validatePassword(user.getPasswordHash(), createJwtRequest.password())) {
            throw new AuthenticationException("The email address or password is invalid");
        }

        return new JwtToken(jwtService.createToken(user));
    }

    private boolean validateEmailAddress(String emailAddress) {

        int maxLength = 255;

        if (emailAddress.length() > maxLength) {
            throw new IllegalArgumentException("The email address supplied is not valid");
        }

        if (!emailAddress.matches("^([a-zA-Z0-9]+(.?[a-zA-Z0-9]+)+)@[a-zA-Z0-9]+(.?[a-zA-Z0-9]+)+$")) {
            throw new IllegalArgumentException("The email address supplied is not valid");
        }

        return true;
    }

    private boolean validatePassword(String passwordHash, String guessedPassword) {
        return BCrypt.checkpw(guessedPassword, passwordHash);
    }
}
