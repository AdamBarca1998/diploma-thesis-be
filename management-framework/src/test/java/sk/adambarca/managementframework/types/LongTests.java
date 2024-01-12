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
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ManagementFrameworkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LongTests extends AbstractTests {

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
            final byte _long = 1;
            final Map<String, Object> params = Map.ofEntries(Map.entry("_long", _long));

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(PrimitivesMResource.class.getSimpleName(), "longAddOne"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = Long.parseLong(response.body());

            assertEquals(200, response.statusCode());
            assertEquals(_long + 1, result);
        }
    }

    @Nested
    class Error {

        @Test
        void testOnDouble() throws URISyntaxException, IOException, InterruptedException {
            final double _long = 4.5;
            final Map<String, Object> params = Map.ofEntries(Map.entry("_long", _long));

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(PrimitivesMResource.class.getSimpleName(), "longAddOne"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNotTypeErrorMsg(String.valueOf(_long)), result);
        }

        @Test
        void testOnNull() throws URISyntaxException, IOException, InterruptedException {
            final Map<String, Object> params = Map.ofEntries(Map.entry("_long", objectMapper.nullNode()));

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(PrimitivesMResource.class.getSimpleName(), "longAddOne"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getNullErrorMsg() , result);
        }

        @Test
        void testUnderflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE); // min is -2^63
            final Map<String, Object> params = Map.ofEntries(Map.entry("_long", value));

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(PrimitivesMResource.class.getSimpleName(), "longAddOne"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }

        @Test
        void testOverflow() throws URISyntaxException, IOException, InterruptedException {
            final var value = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE); // 2^63-1
            final Map<String, Object> params = Map.ofEntries(Map.entry("_long", value));

            final var request = HttpRequest.newBuilder()
                    .uri(getUri(PrimitivesMResource.class.getSimpleName(), "longAddOne"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                    .build();

            final var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final var result = response.body();

            assertEquals(406, response.statusCode());
            assertEquals(getRangeErrorMsg(String.valueOf(value)) , result);
        }
    }

    @Override
    protected String getTypeName() {
        return "Long or long";
    }
}
