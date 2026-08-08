package github.io.tbusk.problem_tracker.servicediscovery.actuator;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthE2ERestTest {

    @LocalServerPort
    private int port;

    @Test
    void shouldReturnHealthyStatus() {
        given()
                .contentType(ContentType.JSON)
                .port(port)
                .get("/actuator/health")
        .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("status", equalTo("UP"));
    }
}
