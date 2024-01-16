package sk.adambarca.managementframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.supportclasses.CalculatorMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PropertyTests extends AbstractTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }

    private final Map<String, Object> primitiveParams = Map.ofEntries(
            Map.entry("_byte", 1),
            Map.entry("_short", 1),
            Map.entry("_int", 1),
            Map.entry("_long", 1),
            Map.entry("_float", 0.1),
            Map.entry("_double", 0.8),
            Map.entry("_char", '_'), // 95
            Map.entry("_boolean", true)
    );

    @Nested
    class DataStructuresTest {

        @Nested
        class SetTest {
            @Test
            void setType() throws URISyntaxException, IOException, InterruptedException {
                final var params = objectMapper.createObjectNode();
                final var a = objectMapper.createArrayNode()
                        .add(1)
                        .addNull()
                        .add(2)
                        .add(2);
                final var b = objectMapper.createArrayNode()
                        .add(1)
                        .add(2);
                params.set("a", a);
                params.set("b", b);

                final var request = HttpRequest.newBuilder()
                        .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumSets"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                        .build();

                final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final var result = Double.parseDouble(response.body());

                assertEquals(200, response.statusCode());
                assertEquals(6, result);
            }

            @Test
            void emptySet() throws URISyntaxException, IOException, InterruptedException {
                final var params = objectMapper.createObjectNode();
                params.set("a", objectMapper.createArrayNode());
                params.set("b", objectMapper.createArrayNode());

                final var request = HttpRequest.newBuilder()
                        .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumSets"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                        .build();

                final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final var result = Double.parseDouble(response.body());

                assertEquals(200, response.statusCode());
                assertEquals(0, result);
            }
        }
    }

    @Override
    protected String getTypeName() {
        return null;
    }
}
