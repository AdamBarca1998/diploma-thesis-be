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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OptionalTests extends AbstractTests {

    private static final String SUM_OPTIONAL = "sumOptional";
    private static final String GET_LENGTH = "getLength";

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }

    @Nested
    class Success {
        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final var num1 = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("num1", num1),
                    Map.entry("num2", objectMapper.nullNode())
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_OPTIONAL, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(num1, result);
        }

        @Test
        void testNullValidity() throws URISyntaxException, IOException, InterruptedException {
            final var list = objectMapper.createArrayNode().add(1).add(2).addNull();
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("list", list)
            );

            final var response = callFunction(BasicClassesMResource.class, GET_LENGTH, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(2, result);
        }

        @Test
        void testValueValidity() throws URISyntaxException, IOException, InterruptedException {
            final var num1 = 0.5;
            final var num2 = 1;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("num1", num1),
                    Map.entry("num2", num2)
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_OPTIONAL, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(num1 + num2, result);
        }

        @Test
        void testAbsentValidity() throws URISyntaxException, IOException, InterruptedException {
            final var num1 = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("num1", num1)
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_OPTIONAL, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(num1, result);
        }
    }

    @Nested
    class Error {
        @Test
        void testInvalidityTypeOptionalCount() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries();

            final var response = callFunction(BasicClassesMResource.class, SUM_OPTIONAL, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals("Method 'sumOptional' has 1 required properties, not 0!", result);
        }

        @Test
        void testOnDifferentParamName() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("numXYZ", 0.5)
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_OPTIONAL, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(STR."Property 'num1' can't have null value!", result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Enum";
    }
}
