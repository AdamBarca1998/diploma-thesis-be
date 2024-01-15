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
class ByteTests extends AbstractTests {

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
            final byte bytePrim = 0;
            final byte byteWrap = 1;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("bytePrim", bytePrim),
                    Map.entry("byteWrap", byteWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, "byteAdd", params);
            final var result = Integer.parseInt(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(bytePrim + byteWrap, result);
        }

        @Test
        void testOnIntegerPart() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 1.0;
            final byte byteWrap = 2;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("bytePrim", _double),
                    Map.entry("byteWrap", byteWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, "byteAdd", params);
            final var result = Integer.parseInt(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(_double + byteWrap, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testOnDecimalPart() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 4.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("bytePrim", _double),
                    Map.entry("byteWrap", 0)
            );

            final var response = callFunction(PrimitivesMResource.class, "byteAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("bytePrim", objectMapper.nullNode()),
                    Map.entry("byteWrap", 0)
            );

            final var response = callFunction(PrimitivesMResource.class, "byteAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = Byte.MIN_VALUE - 1; // min is -128
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("bytePrim", value),
                    Map.entry("byteWrap", 0)
            );

            final var response = callFunction(PrimitivesMResource.class, "byteAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = Byte.MAX_VALUE + 1; // max is 127
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("bytePrim", value),
                    Map.entry("byteWrap", 0)
            );

            final var response = callFunction(PrimitivesMResource.class, "byteAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Byte or byte";
    }
}
