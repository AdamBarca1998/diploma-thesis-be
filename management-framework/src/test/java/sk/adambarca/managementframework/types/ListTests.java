package sk.adambarca.managementframework.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.AbstractTests;
import sk.adambarca.managementframework.ManagementFrameworkApplication;
import sk.adambarca.managementframework.supportclasses.CalculatorMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListTests extends AbstractTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }

    @Nested
    class Success {
        @Test
        void testListType() throws URISyntaxException, IOException, InterruptedException {
            final var params = objectMapper.createObjectNode();
            final var numbersArray = objectMapper.createArrayNode().add(1).addNull().add(2);
            params.set("numbers", numbersArray);

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sum"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(3, result);
        }

        @Test
        void testEmptyList() throws URISyntaxException, IOException, InterruptedException {
            final var params = objectMapper.createObjectNode();
            params.set("numbers", objectMapper.createArrayNode());

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sum"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(0, result);
        }

        @Test
        void testNestedLists() throws URISyntaxException, IOException, InterruptedException {
            final var params = objectMapper.createObjectNode();
            final var numbersArray = objectMapper.createArrayNode()
                    .add(objectMapper.createArrayNode().add(1).add(2))
                    .add(objectMapper.createArrayNode().add(-1).add(5));
            params.set("numbers", numbersArray);

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumNestedLists"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Double.parseDouble(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(7, result);
        }
    }

    @Nested
    class Error {

//        @Test
//        void testNotValidType() throws URISyntaxException, IOException, InterruptedException {
//            final var params = objectMapper.createObjectNode();
//            final var numbersArray = objectMapper.createArrayNode()
//                    .add(objectMapper.createArrayNode().add(1).add(2.1)) // int and double
//                    .add(objectMapper.createArrayNode().add(true).add(5)); // bool and int
//            params.set("numbers", numbersArray);
//
//            final var request = HttpRequest.newBuilder()
//                    .uri(getUri(CalculatorMResource.class.getSimpleName(), "sumNestedLists"))
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
//                    .build();
//
//            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            final var result = Double.parseDouble(response.body());
//
//            assertEquals(200, response.statusCode());
//            assertEquals(7, result);
//        }
    }

    @Override
    protected String getTypeName() {
        return null;
    }
}
