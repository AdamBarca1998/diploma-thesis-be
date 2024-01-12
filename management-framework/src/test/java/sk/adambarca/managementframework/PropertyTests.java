package sk.adambarca.managementframework;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.supportclasses.Argument;
import sk.adambarca.managementframework.supportclasses.CalculatorMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
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
        setIncl(JsonInclude.Include.NON_NULL);
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
    class OptionalTest {
        @Test
        void allArgsOptional() throws URISyntaxException, IOException, InterruptedException {
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
        void partArgsOptional() throws URISyntaxException, IOException, InterruptedException {
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
        void notValidOptionalCount() throws URISyntaxException, IOException, InterruptedException {
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
        void notValidOptionalValue() throws URISyntaxException, IOException, InterruptedException {
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

        @Nested
        class MapTest {
            @Test
            void mapType() throws URISyntaxException, IOException, InterruptedException {
                final var params = objectMapper.createObjectNode();
                final var map = objectMapper.createObjectNode();
                map.set("a", objectMapper.createArrayNode().add(1));
                map.set("b", objectMapper.createArrayNode().add(1).add(2));
                map.set("empty", objectMapper.createArrayNode());
                params.set("map", map);


                final var request = HttpRequest.newBuilder()
                        .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumMap"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                        .build();

                final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final var result = Double.parseDouble(response.body());

                assertEquals(200, response.statusCode());
                assertEquals(4, result);
            }

            @Test
            void mapTypeInteger() throws URISyntaxException, IOException, InterruptedException {
                final var params = objectMapper.createObjectNode();
                final var map = objectMapper.createObjectNode();
                map.set("1", objectMapper.createArrayNode().add(1));
                map.set("2", objectMapper.createArrayNode().add(1).add(2));
                map.set("3", objectMapper.createArrayNode());
                params.set("map", map);


                final var request = HttpRequest.newBuilder()
                        .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumMapInteger"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                        .build();

                final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final var result = Double.parseDouble(response.body());

                assertEquals(200, response.statusCode());
                assertEquals(4, result);
            }

            @Test
            void emptyMap() throws URISyntaxException, IOException, InterruptedException {
                final var params = objectMapper.createObjectNode();
                params.set("map", objectMapper.createObjectNode());

                final var request = HttpRequest.newBuilder()
                        .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumMap"))
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

    @Nested
    class PojoTest {
        @Test
        void sumArgumentMProperty() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("args", new Argument(
                            1.0,
                            List.of(
                                    new Argument(
                                            2.0,
                                            List.of()
                                    ),
                                    new Argument(
                                            3.0,
                                            List.of(new Argument(
                                                    4.0,
                                                    List.of()
                                            ))
                                    )
                            )
                    ))
            );

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumArgumentMProperty"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(10.0, result);
        }
    }

    @Override
    protected String getTypeName() {
        return null;
    }
}
