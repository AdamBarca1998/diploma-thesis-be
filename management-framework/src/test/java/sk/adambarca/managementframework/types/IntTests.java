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
class IntTests extends AbstractTests {

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
            final int intPrim = 1;
            final int intWrap = 2;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("intPrim", intPrim),
                    Map.entry("intWrap", intWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, "intAdd", params);
            final var result = Integer.parseInt(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(intPrim + intWrap, result);
        }

        @Test
        void testOnIntegerPart() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 1.0;
            final int intWrap = 2;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("intPrim", _double),
                    Map.entry("intWrap", intWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, "intAdd", params);
            final var result = Integer.parseInt(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(_double + intWrap, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testOnDecimalPart() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 1.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("intPrim", _double),
                    Map.entry("intWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "intAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("intPrim", objectMapper.nullNode()),
                    Map.entry("intWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "intAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = Integer.MIN_VALUE - 1L; // min is -2^31
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("intPrim", value),
                    Map.entry("intWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "intAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = Integer.MAX_VALUE + 1L; // 2^31-1
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("intPrim", value),
                    Map.entry("intWrap", 2)
            );

            final var response = callFunction(PrimitivesMResource.class, "intAdd", params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Integer or int";
    }
}
