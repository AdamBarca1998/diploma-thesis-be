package sk.adambarca.managementframework.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.Operator;
import sk.adambarca.managementframework.supportclasses.PrimitivesMResource;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FloatTests extends AbstractTests {

    private static final String METHOD = "floatAdd";

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
            final float floatPrim = 0.5f;
            final float floatWrap = 1.0f;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", floatWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Float.parseFloat(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(floatPrim + floatWrap, result);
        }

        @Test
        void testNegative() throws URISyntaxException, IOException, InterruptedException {
            final float floatPrim = -0.84155f;
            final float floatWrap = 1.5958f;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", floatWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Float.parseFloat(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(floatPrim + floatWrap, result);
        }

        @Test
        void testOnInteger() throws URISyntaxException, IOException, InterruptedException {
            final float floatPrim = 0.5f;
            final int _int = 9;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", _int)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Float.parseFloat(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(floatPrim + _int, result);
        }

        @Test
        void testMin() throws URISyntaxException, IOException, InterruptedException {
            final float floatPrim = -Float.MAX_VALUE;
            final float floatWrap = 1.5958f;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", floatWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Float.parseFloat(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(floatPrim + floatWrap, result);
        }

        @Test
        void testMax() throws URISyntaxException, IOException, InterruptedException {
            final float floatPrim = Float.MAX_VALUE;
            final float floatWrap = -1.5958f;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", floatWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Float.parseFloat(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(floatPrim + floatWrap, result);
        }

        @Test
        void testZero() throws URISyntaxException, IOException, InterruptedException {
            final float floatPrim = 0;
            final float floatWrap = 1.5958f;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", floatWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Float.parseFloat(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(floatPrim + floatWrap, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidity() throws URISyntaxException, IOException, InterruptedException {
            final double floatPrim = 9.65;
            final Operator floatWrap = Operator.SUB;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", floatWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(floatWrap)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", objectMapper.nullNode()),
                    Map.entry("floatWrap", 0.5f)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = new BigDecimal(Float.toString(-Float.MAX_VALUE)).subtract(BigDecimal.ONE);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", value),
                    Map.entry("floatWrap", 0.5f)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = new BigDecimal(Float.toString(Float.MAX_VALUE)).add(BigDecimal.ONE);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", value),
                    Map.entry("floatWrap", 0.5f)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Float or float";
    }
}
