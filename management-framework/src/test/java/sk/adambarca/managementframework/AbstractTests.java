package sk.adambarca.managementframework;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.Map;
import java.util.stream.Collectors;

abstract class AbstractTests {

    private static final String SCHEME = "http";
    private static final String MANAGEMENT_PATH = "/management";
    private static final String SERVER = "localhost";
    private int port;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    public void setPort(int portS) {
        port = portS;
    }

    protected final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    protected URI getUriWithParams(Map<String, Object> params, String ...resources) throws URISyntaxException {
        final String path = MANAGEMENT_PATH + (resources.length > 0 ? "/" : "") + String.join("/", resources);
        final String queryString = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        if (queryString.isEmpty()) {
            return new URI(SCHEME, null, SERVER, port, path, null, null);
        } else {
            return new URI(SCHEME, null, SERVER, port, path, queryString, null);
        }
    }
}
