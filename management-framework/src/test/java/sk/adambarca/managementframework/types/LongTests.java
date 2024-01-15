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
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LongTests extends AbstractTests {

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
            final long longPrim = 1;
            final long longWrap = 2;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("longPrim", longPrim),
                    Map.entry("longWrap", longWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, "longAdd", params);
            final var result = Long.parseLong(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(longPrim + longWrap, result);
        }

        @Test
        void testOnIntegerPart() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 1.0;
            final long longWrap = 2;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("longPrim", _double),
                    Map.entry("longWrap", longWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, "longAdd", params);
            final var result = Integer.parseInt(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(_double + longWrap, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testOnDecimalPart() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 1.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("longPrim", _double),
                    Map.entry("longWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "longAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("longPrim", objectMapper.nullNode()),
                    Map.entry("longWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "longAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE); // min is -2^63
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("longPrim", value),
                    Map.entry("longWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "longAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE); // 2^63-1
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("longPrim", value),
                    Map.entry("longWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "longAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Long or long";
    }
}
