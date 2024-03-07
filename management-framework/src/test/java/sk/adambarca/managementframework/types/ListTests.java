package sk.adambarca.managementframework.types;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListTests extends AbstractTests {

    private static final String ADD_ZERO = "addZero";
    private static final String SUM_NESTED_LISTS = "sumNestedLists";
    
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
            final var numbersArray = objectMapper.createArrayNode().add(1).add(2);
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("numbers", numbersArray)
            );

            final var response = callFunction(DataStructuresMResource.class, ADD_ZERO, params);
            final var result = objectMapper.readValue(response.body(), new TypeReference<List<Double>>(){});

            assertEquals(200, response.statusCode());
            assertEquals(numbersArray.size() + 1, result.size());
        }

        @Test
        void testEmpty() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("numbers",  objectMapper.createArrayNode())
            );

            final var response = callFunction(DataStructuresMResource.class, ADD_ZERO, params);
            final var result = objectMapper.readValue(response.body(), new TypeReference<List<Double>>(){});

            assertEquals(200, response.statusCode());
            assertEquals(1, result.size());
        }

        @Test
        void testNestedLists() throws URISyntaxException, IOException, InterruptedException {
            final var numbersArray = objectMapper.createArrayNode()
                    .add(objectMapper.createArrayNode().add(1).add(2))
                    .add(objectMapper.createArrayNode().add(-1).add(5));
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("nestedList",  numbersArray)
            );

            final var response = callFunction(DataStructuresMResource.class, SUM_NESTED_LISTS, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(7, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidityType() throws URISyntaxException, IOException, InterruptedException {
            final var _double = 0.5;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("numbers",  _double)
            );

            final var response = callFunction(DataStructuresMResource.class, ADD_ZERO, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)), result);
        }

        @Test
        void testNullValidity() throws URISyntaxException, IOException, InterruptedException {
            final var numbersArray = objectMapper.createArrayNode().add(1).add(2).addNull(); // addNull()
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("numbers", numbersArray)
            );

            final var response = callFunction(DataStructuresMResource.class, ADD_ZERO, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg().replace("List", "Double or double"), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(Map.entry("numbers", objectMapper.nullNode()));

            final var response = callFunction(DataStructuresMResource.class, ADD_ZERO, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testDifferentTypes() throws URISyntaxException, IOException, InterruptedException {
            final var _double = 0.5;
            final var numbersArray = objectMapper.createArrayNode()
                    .add(objectMapper.createArrayNode().add(1).add(2).add(_double)) // ints and double
                    .add(objectMapper.createArrayNode().add(-1).add(5))
                    ;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("nestedList",  numbersArray)
            );

            final var response = callFunction(DataStructuresMResource.class, SUM_NESTED_LISTS, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_double)).replace("List", "Integer or int"), result);
        }
    }

    @Override
    protected String getTypeName() {
        return "List";
    }
}
