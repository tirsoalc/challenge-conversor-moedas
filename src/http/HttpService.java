package http;

import java.util.Optional;

public interface HttpService {
    Optional<String> get(String url);
}
