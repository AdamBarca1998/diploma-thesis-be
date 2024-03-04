package sk.adambarca.managementframework.types;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.DataStructuresMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MapTests extends AbstractTests {

    private static final String GET_KEYS = "getKeys";
    private static final String GET_VALUES_FROM_NESTED_MAP = "getValuesFromNestedMap";

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
            final var map = Map.ofEntries(
                    Map.entry("key1", 1),
                    Map.entry("key2", 2),
                    Map.entry("key3", 3)
            );
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("map", map)
            );

            final var response = callFunction(DataStructuresMResource.class, GET_KEYS, params);
            final var result = objectMapper.readValue(response.body(), new TypeReference<Set<String>>(){});

            assertEquals(200, response.statusCode());
            assertEquals(map.keySet(), result);
        }

        @Test
        void testEmpty() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("map", Map.of())
            );

            final var response = callFunction(DataStructuresMResource.class, GET_KEYS, params);
            final var result = objectMapper.readValue(response.body(), new TypeReference<List<String>>(){});

            assertEquals(200, response.statusCode());
            assertEquals(List.of(), result);
        }

        @Test
        void testNestedMaps() throws URISyntaxException, IOException, InterruptedException {
            final var _value = "ValueString";
            final var nestedMap = Map.ofEntries(
                    Map.entry("key1", Map.ofEntries(Map.entry(1, _value)))
            );
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("nestedMap",  nestedMap)
            );

            final var response = callFunction(DataStructuresMResource.class, GET_VALUES_FROM_NESTED_MAP, params);
            final var result = objectMapper.readValue(response.body(), new TypeReference<List<String>>(){});

            assertEquals(200, response.statusCode());
            assertEquals(List.of(_value), result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidity() throws URISyntaxException, IOException, InterruptedException {
            final var _double = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("map", _double)
            );

            final var response = callFunction(DataStructuresMResource.class, GET_KEYS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testNullValidity() throws URISyntaxException, IOException, InterruptedException {
            final var map = objectMapper.createObjectNode().set("key1", objectMapper.nullNode()); // addNull()
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("map", map)
            );

            final var response = callFunction(DataStructuresMResource.class, GET_KEYS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg().replace("Map", "Integer or int"), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("map", objectMapper.nullNode())
            );

            final var response = callFunction(DataStructuresMResource.class, GET_KEYS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testDifferentTypes() throws URISyntaxException, IOException, InterruptedException {
            final var _double = 1.5;
            final var map = Map.ofEntries(
                    Map.entry("key1", _double),
                    Map.entry("key2", 2),
                    Map.entry("key3", 3)
            );
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("map", map)
            );

            final var response = callFunction(DataStructuresMResource.class, GET_KEYS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)).replace("Map", "Integer or int"), result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Map";
    }
}
