package seedu.address.commons.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Handles all HTTP/S Requests
 */
public class HttpsClientUtil {

    private static HttpClient client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .build();

    public HttpsClientUtil() {
    }

    public static boolean getLatestExchangeData(String base) throws ExecutionException, InterruptedException {

        // Build request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.exchangeratesapi.io/latest?base=" + base))
            .timeout(Duration.ofSeconds(5))
            .header("Content-Type", "application/json")
            .GET()
            .build();

        // Validatation
        CompletableFuture<Boolean> isSuccess = CompletableFuture.supplyAsync(() ->
            Files.exists(Path.of("data/exchangedata.json")));

        // Async call to endpoint
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofFile(Paths.get("data/exchangedata.json")))
            .thenApply(HttpResponse::body)
            .thenCompose(path -> isSuccess).get();
    }
}
