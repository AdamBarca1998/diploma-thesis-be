package sk.adambarca.managementframework.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.DataStructuresMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SetTests extends AbstractTests {

    private static final String SUM_SETS = "sumSets";
    private static final String GET_COUNT_SETS = "getCountSets";

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
            final var aSet = objectMapper.createArrayNode().add(1).add(2).addNull();
            final var bSet = objectMapper.createArrayNode().add(-1).add(5).add(5).add(5).add(5);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("aSet", aSet),
                    Map.entry("bSet", bSet)
            );

            final var response = callFunction(DataStructuresMResource.class, SUM_SETS, params);
            final var result = Integer.valueOf(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(7, result);
        }

        @Test
        void testEmpty() throws URISyntaxException, IOException, InterruptedException {
            final var _value = 1;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("aSet", Set.of(_value)),
                    Map.entry("bSet", Set.of())
            );

            final var response = callFunction(DataStructuresMResource.class, SUM_SETS, params);
            final var result = Integer.valueOf(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(_value, result);
        }

        @Test
        void testNestedSets() throws URISyntaxException, IOException, InterruptedException {
            final var nestedSet = Set.of(Set.of(1), Set.of(2));
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("nestedSet",  nestedSet)
            );

            final var response = callFunction(DataStructuresMResource.class, GET_COUNT_SETS, params);
            final var result = Long.parseLong(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(2, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidityType() throws URISyntaxException, IOException, InterruptedException {
            final var _double = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("nestedSet",  _double)
            );

            final var response = callFunction(DataStructuresMResource.class, GET_COUNT_SETS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testNullValidity() throws URISyntaxException, IOException, InterruptedException {
            final var aSet = objectMapper.createArrayNode().add(1).add(2);
            final var bSet = objectMapper.createArrayNode().addNull();
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("aSet", aSet),
                    Map.entry("bSet", bSet)
            );

            final var response = callFunction(DataStructuresMResource.class, SUM_SETS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg().replace("Set", "Integer or int"), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("nestedSet",  objectMapper.nullNode())
            );

            final var response = callFunction(DataStructuresMResource.class, GET_COUNT_SETS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testDifferentTypes() throws URISyntaxException, IOException, InterruptedException {
            final var _double = 2.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("nestedSet",  Set.of(Set.of(1), Set.of(_double)))
            );


            final var response = callFunction(DataStructuresMResource.class, GET_COUNT_SETS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)).replace("Set", "Integer or int"), result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Set";
    }
}
