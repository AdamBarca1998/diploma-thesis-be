package sk.adambarca.managementframework.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.CalculatorMResource;
import sk.adambarca.managementframework.supportclasses.Operator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EnumTests extends AbstractTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }

    @Nested
    class Success {
        @Test
        void testValidEnum() throws URISyntaxException, IOException, InterruptedException {
            run(Operator.ADD.name());
        }
    }

    @Nested
    class Error {

        @Test
        void testNotValidEnum() {
            try {
                run("XYZ");
                fail();
            } catch (Exception _) {
            }
        }
    }

    private void run(String enumS) throws URISyntaxException, IOException, InterruptedException {
        final var params = objectMapper.createObjectNode();
        params.set("numbers", objectMapper.createArrayNode().add(1).addNull().add(2));
        params.put("operator", enumS);

        final var request = HttpRequest.newBuilder()
                .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumByEnum"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                .build();

        final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        final var result = Double.parseDouble(response.body());

        assertEquals(200, response.statusCode());
        assertEquals(3.0, result);
    }
}
