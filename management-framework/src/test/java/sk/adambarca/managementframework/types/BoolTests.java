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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoolTests extends AbstractTests {

    private static final String METHOD = "boolAnd";

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
            final boolean boolPrim = true;
            final boolean boolWrap = true;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("boolPrim", boolPrim),
                    Map.entry("boolWrap", boolWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Boolean.parseBoolean(response.body());

            assertEquals(200, response.statusCode());
            assertTrue(result);
        }

        @Test
        void testOnZeroAndOne() throws URISyntaxException, IOException, InterruptedException {
            final int boolPrim = 0;
            final int boolWrap = 1;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("boolPrim", boolPrim),
                    Map.entry("boolWrap", boolWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = Boolean.parseBoolean(response.body());

            assertEquals(200, response.statusCode());
            assertFalse(result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidity() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("boolPrim", _double),
                    Map.entry("boolWrap", true)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("boolPrim", objectMapper.nullNode()),
                    Map.entry("boolWrap", true)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Boolean or boolean";
    }
}
