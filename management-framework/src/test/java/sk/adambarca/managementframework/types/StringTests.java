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
class StringTests extends AbstractTests {

    private static final String METHOD = "sayHello";

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
            final String s = "World";
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", s)
            );

            final var response = callFunction(BasicClassesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."Hello \{s}!", result);
        }

        @Test
        void testEmpty() throws URISyntaxException, IOException, InterruptedException {
            final String empty = "";
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", empty)
            );

            final var response = callFunction(BasicClassesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."Hello \{empty}!", result);
        }

        @Test
        void testSpecial() throws URISyntaxException, IOException, InterruptedException {
            final String special = "这是一些中文字符 + some English 1234567890 !@#$%^&*()_+-=[]{};':\\\",./<>?";
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", special)
            );

            final var response = callFunction(BasicClassesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(200, response.statusCode());
            assertEquals(STR."Hello \{special}!", result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidityType() throws URISyntaxException, IOException, InterruptedException {
            final double _double = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", _double)
            );

            final var response = callFunction(BasicClassesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("s", objectMapper.nullNode())
            );

            final var response = callFunction(BasicClassesMResource.class, METHOD, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "String";
    }
}
