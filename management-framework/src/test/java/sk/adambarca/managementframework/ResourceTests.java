package sk.adambarca.managementframework;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import sk.adambarca.managementframework.resource.Resource;
import sk.adambarca.managementframework.supportclasses.CalculatorMResource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResourceTests extends AbstractTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        setPort(port);
    }

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
}
