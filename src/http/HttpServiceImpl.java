package http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static java.net.http.HttpResponse.BodyHandlers;

public class HttpServiceImpl implements HttpService{

    HttpClient client = HttpClient.newBuilder().build();

    @Override
    public Optional<HttpResponse<String>> get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return Optional.of(response);
        } catch (IOException | InterruptedException e) {
            System.out.println("[Error]: " + e.getMessage());
            return Optional.empty();
        }
    }
}
