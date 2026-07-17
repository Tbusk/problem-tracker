package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateRequestDTO;
import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateSuccessDTO;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes account creation endpoints.
 */
@RestController
@RequestMapping("/api/v1/create-account")
public class CreateAccountController {

    private CreateAccountService createAccountService;

    /**
     * @param createAccountService service responsible for creating accounts
     */
    public CreateAccountController(CreateAccountService createAccountService) {
        this.createAccountService = createAccountService;
    }

    /**
     * Creates a new user account from the given request.
     *
     * @param createAccountDTO dto containing the user's email address and password
     * @return a success response dto with a confirmation message
     * @throws InvalidPasswordException if the supplied password does not meet requirements
     * @throws IllegalArgumentException if the supplied email or password are invalid or if the user already exists
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateSuccessDTO createAccount(@RequestBody CreateRequestDTO createAccountDTO) throws InvalidPasswordException {
        return createAccountService.create(createAccountDTO);
    }

}
