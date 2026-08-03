package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateRequestDTO;
import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateSuccessDTO;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.AccountServiceException;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.EmailAddressInUseException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.InvalidEmailException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.StateException;
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
     * @throws IllegalArgumentException if any required field is null
     * @throws InvalidEmailException if the email address is not in a valid format
     * @throws EmailAddressInUseException if the email address is already associated with an existing account
     * @throws StateException if the default role is not found
     */
    public CreateSuccessDTO create(CreateRequestDTO request) throws AccountServiceException {

        if (request == null) {
            throw new IllegalArgumentException("Request body cannot be empty");
        }

        if (request.emailAddress() == null) {
            throw new IllegalArgumentException("Please supply an email address");
        }

        if (!request.emailAddress().matches("^[a-zA-Z0-9]+(\\.?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]{2,})+$")) {
            throw new InvalidEmailException();
        }

        if (request.password() == null) {
            throw new IllegalArgumentException("Please supply a password");
        }

        Optional<User> existingUser = userRepository.findByEmailAddress(request.emailAddress());

        if (existingUser.isPresent()) {
            throw new EmailAddressInUseException();
        }

        passwordCheckerService.isValidPassword(request.password());

        String encodedPassword = passwordEncoder.encode(request.password());

        Optional<Role> defaultRole = roleRepository.findByName(Role.DEFAULT_ROLE_NAME);

        if (!defaultRole.isPresent()) {
            throw new StateException("Default role was not found when it is expected.");
        }

        User newUser = new User(request.emailAddress(), encodedPassword, defaultRole.get());

        userRepository.save(newUser);

        return new CreateSuccessDTO("Account successfully created! Please log in.");
    }
}
