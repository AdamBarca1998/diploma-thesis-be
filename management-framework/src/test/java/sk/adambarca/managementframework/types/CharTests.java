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
class CharTests extends AbstractTests {

    private static final String METHOD = "charConcat";

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }


    @Nested
    class Success {
        @Test
        void testValidityType() throws URISyntaxException, IOException, InterruptedException {
            final char charPrim = 'A';
            final Character charWrap = 'B';
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", charPrim),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{charPrim}\{charWrap}", result);
        }

        @Test
        void testOnIntegerPart() throws URISyntaxException, IOException, InterruptedException {
            final char charPrim = 'A';
            final double charWrap = 66.0; // 'B'
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", charPrim),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{charPrim}\{(char) charWrap}", result);
        }

        @Test
        void testOnInteger() throws URISyntaxException, IOException, InterruptedException {
            final char charPrim = 'A';
            final int charWrap = 66; // 'B'
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", charPrim),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{charPrim}\{(char) charWrap}", result);
        }

        @Test
        void testOnSpecial() throws URISyntaxException, IOException, InterruptedException {
            final char special = '\t';
            final Character charWrap = 'B';
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", special),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{special}\{charWrap}", result);
        }

        @Test
        void testOnEmpty() throws URISyntaxException, IOException, InterruptedException {
            final char empty = '\u0000';
            final Character charWrap = 'B';
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", empty),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{empty}\{charWrap}", result);
        }

        @Test
        void testMin() throws URISyntaxException, IOException, InterruptedException {
            final var min = Character.MIN_VALUE;
            final Character charWrap = 'B';
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", min),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{min}\{charWrap}", result);
        }

        @Test
        void testMax() throws URISyntaxException, IOException, InterruptedException {
            final var max = Character.MAX_VALUE;
            final Character charWrap = 'B';
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", max),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{max}\{charWrap}", result);
        }

        @Test
        void testZero() throws URISyntaxException, IOException, InterruptedException {
            final var zero = 0;
            final Character charWrap = 'B';
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", zero),
                    Map.entry("charWrap", charWrap)
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."\{Character.MIN_VALUE}\{charWrap}", result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidityType() throws URISyntaxException, IOException, InterruptedException {
            final String _s = "ABC";
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", _s),
                    Map.entry("charWrap", 'B')
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(_s), result);
        }

        @Test
        void testOnDecimalPart() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 4.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", _double),
                    Map.entry("charWrap", 'B')
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", objectMapper.nullNode()),
                    Map.entry("charWrap", 'B')
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigDecimal.valueOf(Character.MIN_VALUE).subtract(BigDecimal.ONE);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", value),
                    Map.entry("charWrap", 'B')
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg("-1") , result);
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigDecimal.valueOf(Character.MAX_VALUE).add(BigDecimal.ONE);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("charPrim", value),
                    Map.entry("charWrap", 'B')
            );

            final var response = callFunction(PrimitivesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Character or char";
    }
}
