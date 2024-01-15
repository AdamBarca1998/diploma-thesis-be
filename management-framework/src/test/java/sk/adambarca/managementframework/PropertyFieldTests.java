package sk.adambarca.managementframework;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.resource.Resource;
import sk.adambarca.managementframework.supportclasses.MemoryMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyFieldTests extends AbstractTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }

    @Override
    protected String getTypeName() {
        return null;
    }

    @Nested
    class HideTest {

        @Test
        void isHide() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(MemoryMResource.class.getSimpleName()))
                    .GET()
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final Resource resource = objectMapper.readValue(response.body(), new TypeReference<>() {});

            assertEquals(200, response.statusCode());
            resource.properties().forEach(property -> assertNotEquals("secretKey", property.name()));
        }
    }

    @Nested
    class DisableTest {

        @Test
        void isDisable() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(MemoryMResource.class.getSimpleName()))
                    .GET()
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final Resource resource = objectMapper.readValue(response.body(), new TypeReference<>() {});
            final var id = resource.properties().stream()
                    .filter(property -> property.name().equals("id"))
                    .findFirst()
                    .orElseThrow();

            assertEquals(200, response.statusCode());
            assertEquals(List.of("Disable"), id.validations());
        }
    }

    @Nested
    class EnableTest {

        @Test
        void IsEnable() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(MemoryMResource.class.getSimpleName()))
                    .GET()
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final Resource resource = objectMapper.readValue(response.body(), new TypeReference<>() {});
            final var value = resource.properties().stream()
                    .filter(property -> property.name().equals("value"))
                    .findFirst()
                    .orElseThrow();

            assertEquals(200, response.statusCode());
            assertEquals(List.of(), value.validations());
        }

        @Test
        void setAndGet() throws URISyntaxException, IOException, InterruptedException {
            final var value = 10.1;
            final Map<String, Object> params = Map.ofEntries(Map.entry("value", value));
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(MemoryMResource.class.getSimpleName()))
                    .GET()
                    .build();

            final var oldResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var responseSet = callFunction(MemoryMResource.class, "setValue", params);
            final var responseGet = callFunction(MemoryMResource.class, "getValue", Map.ofEntries());
            final var newResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            final Resource oldResource = objectMapper.readValue(oldResponse.body(), new TypeReference<>() {});
            final var oldValue = oldResource.properties().stream()
                    .filter(property -> property.name().equals("value"))
                    .findFirst()
                    .orElseThrow();
            final Resource newResource = objectMapper.readValue(newResponse.body(), new TypeReference<>() {});
            final var newValue = newResource.properties().stream()
                    .filter(property -> property.name().equals("value"))
                    .findFirst()
                    .orElseThrow();

            assertEquals(200, oldResponse.statusCode());
            assertEquals(200, responseSet.statusCode());
            assertEquals(200, responseGet.statusCode());
            assertNull(oldValue.value());
            assertEquals(value, Double.parseDouble(responseGet.body()));
            assertEquals(value, newValue.value());
        }
    }
}
