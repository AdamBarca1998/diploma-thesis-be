package sk.adambarca.managementframework.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.PrimitivesMResource;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DoubleTests extends AbstractTests {

    private static final String METHOD = "doubleAdd";

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }


    @Nested
    class Success {
        @Test
        void testPositive() throws URISyntaxException, IOException, InterruptedException {
            final double doublePrim = 0.453235;
            final double doubleWrap = 1.015578;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", doublePrim),
                    Map.entry("doubleWrap", doubleWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(doublePrim + doubleWrap, result);
        }

        @Test
        void testNegative() throws URISyntaxException, IOException, InterruptedException {
            final double doublePrim = -0.4592;
            final double doubleWrap = -1.74546;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", doublePrim),
                    Map.entry("doubleWrap", doubleWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(doublePrim + doubleWrap, result);
        }

        @Test
        void testOnInteger() throws URISyntaxException, IOException, InterruptedException {
            final double doublePrim = 0.5;
            final int doubleWrap = 10;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", doublePrim),
                    Map.entry("doubleWrap", doubleWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(doublePrim + doubleWrap, result);
        }

        @Test
        void testMin() throws URISyntaxException, IOException, InterruptedException {
            final double doublePrim = 1.5;
            final double doubleWrap = -Double.MAX_VALUE;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", doublePrim),
                    Map.entry("doubleWrap", doubleWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(doublePrim + doubleWrap, result);
        }

        @Test
        void testMax() throws URISyntaxException, IOException, InterruptedException {
            final double doublePrim = -1.5;
            final double doubleWrap = Double.MAX_VALUE;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", doublePrim),
                    Map.entry("doubleWrap", doubleWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(doublePrim + doubleWrap, result);
        }

        @Test
        void testZero() throws URISyntaxException, IOException, InterruptedException {
            final double doublePrim = 9.65;
            final double doubleWrap = 0;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", doublePrim),
                    Map.entry("doubleWrap", doubleWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(doublePrim + doubleWrap, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidity() throws URISyntaxException, IOException, InterruptedException {
            final double doublePrim = 9.65;
            final char doubleWrap = 'A';
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", doublePrim),
                    Map.entry("doubleWrap", doubleWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(doubleWrap)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", 0.5),
                    Map.entry("doubleWrap", objectMapper.nullNode())
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigDecimal.valueOf(-Double.MAX_VALUE).subtract(BigDecimal.ONE);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", value),
                    Map.entry("doubleWrap", 1)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(value.toString()) , result); // rounded
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigDecimal.valueOf(Double.MAX_VALUE).add(BigDecimal.ONE);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("doublePrim", value),
                    Map.entry("doubleWrap", 1)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Double or double";
    }
}
