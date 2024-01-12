package sk.adambarca.managementframework.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.BasicClassesMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StringTests extends AbstractTests {

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
            final String s = "World";
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", s)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "sayHello"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."Hello \{s}!", result);
        }

        @Test
        void testEmpty() throws URISyntaxException, IOException, InterruptedException {
            final String empty = "";
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", empty)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "sayHello"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."Hello \{empty}!", result);
        }
    }

    @Nested
    class Error {

        @Test
        void testOnDouble() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", _double)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "sayHello"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", objectMapper.nullNode())
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(BasicClassesMResource.class.getSimpleName(), "sayHello"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "String";
    }
}
