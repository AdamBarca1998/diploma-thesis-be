package sk.adambarca.managementframework.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.BasicClassesMResource;
import sk.adambarca.managementframework.supportclasses.Operator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        void testValidity() throws URISyntaxException, IOException, InterruptedException {
            final int number = 0;
            final Operator operator = Operator.ADD;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("number", number),
                    Map.entry("operator", operator)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "addOneByEnum"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Integer.parseInt(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(number + 1, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testOnDouble() throws URISyntaxException, IOException, InterruptedException {
            final int number = 0;
            final double _double = 1.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("number", number),
                    Map.entry("operator", _double)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "addOneByEnum"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testInvalidity() throws URISyntaxException, IOException, InterruptedException {
            final int number = 0;
            final String invalid = "XYZ";
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("number", number),
                    Map.entry("operator", invalid)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "addOneByEnum"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(invalid), result);
        }

        @Test
        void testOnInteger() throws URISyntaxException, IOException, InterruptedException {
            final int number = 1;
            final int operator = 0;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("number", number),
                    Map.entry("operator", operator)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "addOneByEnum"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(operator)), result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Enum";
    }
}
