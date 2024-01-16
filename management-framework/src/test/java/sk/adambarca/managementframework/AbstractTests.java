package sk.adambarca.managementframework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public abstract class AbstractTests {

    private static final String SCHEME = "http";
    private static final String MANAGEMENT_PATH = "/management";
    private static final String SERVER = "localhost";
    private int port;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    public AbstractTests() {
        objectMapper.registerModule(new Jdk8Module());
    }

    public void setPort(int port) {
        this.port = port;
    }

    protected final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    protected URI getUri(String ...resources) throws URISyntaxException {
        final String path = MANAGEMENT_PATH + (resources.length > 0 ? "/" : "") + String.join("/", resources);

        return new URI(SCHEME, null, SERVER, port, path, null, null);
    }

    protected String getNotTypeErrorMsg(String value) {
        return STR."The value '\{value}' is not of type \{getTypeName()}!";
    }

    protected String getRangeErrorMsg(String value) {
        return STR."The value '\{ value }' is out of range for \{getTypeName()}!";
    }

    protected String getNullErrorMsg() {
        return STR."Using 'null' directly for \{getTypeName()} is not allowed." +
                " If you intend to represent an optional value, it's recommended to use the Optional wrapper." +
                " For example, you can use Optional<Byte>.";
    }

    protected abstract String getTypeName();

    protected HttpResponse<String> callFunction(Class clazz, String method, Map<String, Object> params)
            throws IOException, InterruptedException, URISyntaxException
    {
        final var request = HttpRequest.newBuilder()
                .uri(getUri(clazz.getSimpleName(), method))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(params)))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
