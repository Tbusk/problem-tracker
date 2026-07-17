package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateRequestDTO;
import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateSuccessDTO;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import github.io.tbusk.problem_tracker.accountservice.role.Role;
import github.io.tbusk.problem_tracker.accountservice.role.RoleRepository;
import github.io.tbusk.problem_tracker.accountservice.user.User;
import github.io.tbusk.problem_tracker.accountservice.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for creating and persisting new user accounts.
 */
@Service
public class CreateAccountService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private PasswordCheckerService passwordCheckerService;

    /**
     * Creates a service instance with the required repositories and utilities.
     *
     * @param userRepository         repository for persisting user accounts
     * @param roleRepository         repository for resolving roles
     * @param passwordEncoder        encoder for hashing the user's password
     * @param passwordCheckerService utility for validating password strength
     */
    public CreateAccountService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, PasswordCheckerService passwordCheckerService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordCheckerService = passwordCheckerService;
    }

    /**
     * Creates a new user account from the given request.
     * Validates the input, checks for duplicate email addresses, hashes the password, assigns the default
     * role, and persists the user to the database.
     *
     * @param request the DTO containing the user's email address and password
     * @return a success response indicating the account was created
     * @throws InvalidPasswordException if the supplied password does not meet requirements
     * @throws IllegalArgumentException if any required field is null or the email address is already in use
     */
    public CreateSuccessDTO create(CreateRequestDTO request) throws InvalidPasswordException {

        if (request == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        if (request.emailAddress() == null) {
            throw new IllegalArgumentException("Please supply an email address");
        }

        if (request.password() == null) {
            throw new IllegalArgumentException("Please supply a password");
        }

        Optional<User> existingUser = userRepository.findByEmailAddress(request.emailAddress());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("It looks like this email address is already in use. Please log in instead.");
        }

        passwordCheckerService.isValidPassword(request.password());

        String encodedPassword = passwordEncoder.encode(request.password());

        Optional<Role> defaultRole = roleRepository.findByName(Role.DEFAULT_ROLE_NAME);

        if (!defaultRole.isPresent()) {
            throw new IllegalStateException("Default role not found");
        }

        User newUser = new User(request.emailAddress(), encodedPassword, defaultRole.get());

        userRepository.save(newUser);

        return new CreateSuccessDTO("Account successfully created! Please log in.");
    }
}
