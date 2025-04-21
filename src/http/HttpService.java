package http;

import java.net.http.HttpResponse;
import java.util.Optional;

public interface HttpService {
    Optional<HttpResponse<String>> get(String url);
}
