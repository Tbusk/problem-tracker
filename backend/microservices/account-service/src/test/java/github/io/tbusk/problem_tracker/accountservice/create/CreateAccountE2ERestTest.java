package github.io.tbusk.problem_tracker.accountservice.create;

import github.io.tbusk.problem_tracker.accountservice.create.exceptions.EmailAddressInUseException;
import github.io.tbusk.problem_tracker.accountservice.exceptions.InvalidEmailException;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateAccountE2ERestTest {

    @LocalServerPort
    private int port;

    @Test
    @Sql(statements = "TRUNCATE TABLE \"USER\"", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnSuccessResponse() {

        String emailAddress = "test.user@example.com";
        String password = "securePassword!1";

        given()
                .contentType(ContentType.JSON)
                .port(port)
                .body(
                        //language=json
                        String.format("""
                        {
                            "emailAddress": "%s",
                            "password": "%s"
                        }
                        """, emailAddress, password)
                ).
        post("/api/v1/create-account")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("message", response ->
                        equalTo(CreateAccountService.SUCCESS_RESPONSE)
                )
        ;
    }

    @Test
    @Sql(statements = "TRUNCATE TABLE \"USER\"", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnFailureResponseWhenEmailIsInvalid() {
        String emailAddress = "";
        String password = "securePassword!1";

        given()
                .contentType(ContentType.JSON)
                .port(port)
                .body(
                        //language=json
                        String.format("""
                        {
                            "emailAddress": "%s",
                            "password": "%s"
                        }
                        """, emailAddress, password)
                ).
                post("/api/v1/create-account")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("message", response ->
                        equalTo(InvalidEmailException.MESSAGE)
                );
    }

    @Test
    @Sql(statements = "TRUNCATE TABLE \"USER\"", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnFailureResponseWhenInvalidArgs() {
        String emailAddress = "test.user@example.com";

        given()
                .contentType(ContentType.JSON)
                .port(port)
                .body(
                        //language=json
                        String.format("""
                        {
                            "emailAddress": "%s"
                        }
                        """, emailAddress)
                ).
                post("/api/v1/create-account")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("message", response ->
                        equalTo("Invalid argument: Please supply a password")
                );
    }

    @Test
    @Sql(statements = "TRUNCATE TABLE \"USER\"", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnFailureResponseWhenInvalidPassword() {
        String emailAddress = "test.user@example.com";
        String password = "dolphins";

        given()
                .contentType(ContentType.JSON)
                .port(port)
                .body(
                        //language=json
                        String.format("""
                        {
                            "emailAddress": "%s",
                            "password": "%s"
                        }
                        """, emailAddress, password)
                ).
                post("/api/v1/create-account")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("message", response ->
                        equalTo("Password is missing uppercase, digit, special character")
                );
    }

    @Test
    @Sql(statements = "TRUNCATE TABLE \"USER\"", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldReturnFailureResponseWhenEmailIsTaken() {
        String emailAddress = "test.user@example.com";
        String password = "securePassword!1";

        given()
                .contentType(ContentType.JSON)
                .port(port)
                .body(
                        //language=json
                        String.format("""
                        {
                            "emailAddress": "%s",
                            "password": "%s"
                        }
                        """, emailAddress, password)
                ).
                post("/api/v1/create-account")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .port(port)
                .body(
                        //language=json
                        String.format("""
                        {
                            "emailAddress": "%s",
                            "password": "%s"
                        }
                        """, emailAddress, password)
                ).
                post("/api/v1/create-account")
                .then()
                .statusCode(409)
                .contentType(ContentType.JSON)
                .body("message", response ->
                        equalTo(EmailAddressInUseException.MESSAGE)
                )
        ;
    }
}
