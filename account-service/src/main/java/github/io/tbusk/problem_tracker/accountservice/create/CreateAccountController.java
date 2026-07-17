package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateRequestDTO;
import github.io.tbusk.problem_tracker.accountservice.create.dtos.CreateSuccessDTO;
import github.io.tbusk.problem_tracker.accountservice.create.exceptions.InvalidPasswordException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/create-account")
public class CreateAccountController {

    private CreateAccountService createAccountService;

    public CreateAccountController(CreateAccountService createAccountService) {
        this.createAccountService = createAccountService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateSuccessDTO createAccount(@RequestBody CreateRequestDTO createAccountDTO) throws InvalidPasswordException {
        return createAccountService.create(createAccountDTO);
    }

}
