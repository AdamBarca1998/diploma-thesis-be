package sk.adambarca.managementframework;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

public abstract class AbstractTests {

    private static final String SCHEME = "http";
    private static final String MANAGEMENT_PATH = "/management";
    private static final String SERVER = "localhost";
    private int port;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    public void setIncl(JsonInclude.Include incl) {
        objectMapper.setSerializationInclusion(incl);
        objectMapper.nullNode();
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

    protected String getNotTypeErrorMsg(String _value) {
        return STR."The \{_value} is not of type \{getTypeName()}!";
    }

    protected String getRangeErrorMsg(String _value) {
        return STR."The \{ _value } is out of range for \{getTypeName()}!";
    }

    protected String getNullErrorMsg() {
        return STR."Using 'null' directly for \{getTypeName()} is not allowed." +
                " If you intend to represent an optional value, it's recommended to use the Optional wrapper." +
                " For example, you can use Optional<Byte>.";
    }

    protected abstract String getTypeName();
}
