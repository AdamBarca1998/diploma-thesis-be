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
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FloatTests extends AbstractTests {

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
            final float floatPrim = 0.5f;
            final float floatWrap = 1.0f;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", floatPrim),
                    Map.entry("floatWrap", floatWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, "floatAdd", params);
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

            final var response = callFunction(PrimitivesMResource.class, "floatAdd", params);
            final var result = Float.parseFloat(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(floatPrim + _int, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", objectMapper.nullNode()),
                    Map.entry("floatWrap", 0.5f)
            );

            final var response = callFunction(PrimitivesMResource.class, "floatAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = -Float.MAX_VALUE - 1;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", value),
                    Map.entry("floatWrap", 0.5f)
            );

            final var response = callFunction(PrimitivesMResource.class, "floatAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = Float.MAX_VALUE + 1;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("floatPrim", value),
                    Map.entry("floatWrap", 0.5f)
            );

            final var response = callFunction(PrimitivesMResource.class, "floatAdd", params);
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
