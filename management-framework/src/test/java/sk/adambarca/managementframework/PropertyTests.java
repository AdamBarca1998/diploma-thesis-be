package sk.adambarca.managementframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.testclasses.CalculatorMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    class TypeTest {
        @Test
        void testPrimitiveTypes() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumAllPrimitives"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(primitiveParams)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertTrue(result > 100);
        }

        @Test
        void testPrimitiveWrapperTypes() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumAllPrimitiveWrappers"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(primitiveParams)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertTrue(result > 100);
        }
    }

    @Nested
    class OptionalTest {
        @Test
        void testAllArgsOptional() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("a", 0.1),
                    Map.entry("b", 1),
                    Map.entry("c", 0.2)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumOptionals"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertTrue(result > 1.3);
        }

        @Test
        void testPartArgsOptional() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("c", 0.2)
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumOptionals"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertTrue(result > 0.2);
        }

        @Test
        void testNotValidOptionalCount() throws URISyntaxException, IOException, InterruptedException {
            Map<String, Object> params = new HashMap<>(primitiveParams);
            params.remove("_double");

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumAllPrimitives"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(406, response.statusCode());
            assertEquals("Method 'sumAllPrimitives' has 8 required properties, not 7", response.body());
        }

        @Test
        void testNotValidOptionalValue() throws URISyntaxException, IOException, InterruptedException {
            final var propertyName = "_double";
            Map<String, Object> params = new HashMap<>(primitiveParams);
            params.remove(propertyName);
            params.put("_something", 1.0);

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumAllPrimitives"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(406, response.statusCode());
            assertEquals(STR."Property '\{propertyName}' can't have null value!", response.body());
        }
    }

    @Nested
    class DataStructuresTest {

        @Nested
        class ListTest {
            @Test
            void testListType() throws URISyntaxException, IOException, InterruptedException {
                final Map<String, Object> params = Map.ofEntries(
                        Map.entry("numbers", "[1,null,2]")
                );
                final var request = HttpRequest.newBuilder()
                        .uri(getUri(CalculatorMResource.class.getSimpleName(), "sum"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                        .build();

                final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final var result = Double.parseDouble(response.body());

                assertEquals(200, response.statusCode());
                assertEquals(3, result);
            }

            @Test
            void testEmptyList() throws URISyntaxException, IOException, InterruptedException {
                final Map<String, Object> params = Map.ofEntries(
                        Map.entry("numbers", "[]")
                );
                final var request = HttpRequest.newBuilder()
                        .uri(getUri(CalculatorMResource.class.getSimpleName(), "sum"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                        .build();

                final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final var result = Double.parseDouble(response.body());

                assertEquals(200, response.statusCode());
                assertEquals(0, result);
            }
        }

        @Nested
        class SetTest {
            @Test
            void testSetType() throws URISyntaxException, IOException, InterruptedException {
                final Map<String, Object> params = Map.ofEntries(
                        Map.entry("a", "[1,null,2,2]"),
                        Map.entry("b", "[1,2]")
                );
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
            void testEmptySet() throws URISyntaxException, IOException, InterruptedException {
                final Map<String, Object> params = Map.ofEntries(
                        Map.entry("a", "[]"),
                        Map.entry("b", "[]")
                );
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
}
