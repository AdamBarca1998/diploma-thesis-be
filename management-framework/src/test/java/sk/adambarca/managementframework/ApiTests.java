package sk.adambarca.managementframework;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.resource.Resource;
import sk.adambarca.managementframework.supportclasses.CalculatorMResource;
import sk.adambarca.managementframework.supportclasses.Operator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTests extends AbstractTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }

    @Nested
    class Success {
        @Test
        void testGetAll() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri())
                    .GET()
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            final List<Resource> list = objectMapper.readValue(response.body(), new TypeReference<>() {});

            assertEquals(200, response.statusCode());
            assertNotEquals(0, list.size());
        }

        @Test
        void testFindByType() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(CalculatorMResource.class.getSimpleName()))
                    .GET()
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final Resource resource = objectMapper.readValue(response.body(), new TypeReference<>() {});

            assertEquals(200, response.statusCode());
            assertEquals(CalculatorMResource.class.getSimpleName(), resource.type());
        }

        @Test
        void testInfo() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri(Operator.class.getName(), "info"))
                    .GET()
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode());
        }
    }

    @Nested
    class Error {
        @Test
        void testFindByType() throws URISyntaxException, IOException, InterruptedException {
            final var request = HttpRequest.newBuilder()
                    .uri(getUri("XYZKOKOKO"))
                    .GET()
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode());
            assertEquals("null", response.body());
        }

        @Test
        void testUnsupportedType() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(
                    Map.entry("calendar", Calendar.getInstance())
            );

            final var response = callFunction(CalculatorMResource.class, "isSupportType", params);;

            assertEquals(406, response.statusCode());
        }
    }

    @Override
    protected String getTypeName() {
        return null;
    }
}
