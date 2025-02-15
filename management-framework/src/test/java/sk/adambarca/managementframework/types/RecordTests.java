package sk.adambarca.managementframework.types;

import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.BasicClassesMResource;
import sk.adambarca.managementframework.supportclasses.ErrorPerson;
import sk.adambarca.managementframework.supportclasses.Person;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecordTests extends AbstractTests {

    private static final String SUM_AGES = "sumAges";
    private static final String IS_EMPTY_RECORD = "isEmptyRecord";

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
            final var age = 20;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("person", new Person("Adam", age, null))
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_AGES, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(age, result);
        }

        @Test
        void testOnEmpty() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(Map.entry("emptyRecord", objectMapper.createObjectNode()));

            final var response = callFunction(BasicClassesMResource.class, IS_EMPTY_RECORD, params);
            final var result = Boolean.parseBoolean(response.body());

            assertEquals(200, response.statusCode());
            assertTrue(result);
        }

        @Test
        void testNested() throws URISyntaxException, IOException, InterruptedException {
            final var adamAge = 20;
            final var johnAge = 21;
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("person", new Person("Adam", adamAge, Optional.of(new Person("John", johnAge, null))))
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_AGES, params);
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(adamAge + johnAge, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testInvalidityType() throws URISyntaxException, IOException, InterruptedException {
            final var person = objectMapper.createObjectNode(); // missing age
            person.set("name", new TextNode("Adam"));

            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("person", person)
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_AGES, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertTrue(result.startsWith("Error converting JSON to type"));
            assertTrue(result.contains("The Property 'age' has error:"));
        }

        @Test
        void testNullValidity() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("person", new ErrorPerson(Optional.empty(), 20, null)) // name = null
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_AGES, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertTrue(result.startsWith("Error converting JSON to type"));
            assertTrue(result.contains("The Property 'name' has error:"));
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(Map.entry("person", objectMapper.nullNode()));

            final var response = callFunction(BasicClassesMResource.class, SUM_AGES, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testDifferentTypes() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("person", new ErrorPerson(Optional.of("Adam"), 20.45, null)) // wrong age
            );

            final var response = callFunction(BasicClassesMResource.class, SUM_AGES, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertTrue(result.startsWith("Error converting JSON to type"));
            assertTrue(result.contains("The Property 'age' has error:"));
        }

        @Test
        void testOnEmpty() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(Map.entry("person", objectMapper.createObjectNode()));

            final var response = callFunction(BasicClassesMResource.class, SUM_AGES, params);
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertTrue(result.startsWith("Error converting JSON to type"));
        }
    }

    @Override
    protected String getTypeName() {
        return "Record";
    }
}
