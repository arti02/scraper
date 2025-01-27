package leon.screen.scraper.processing;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.zip.GZIPInputStream;

@Service
public class CreateGetRequestOperation {

    public String execute(String apiPath, boolean isCookiePresent){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(apiPath))
                    .header("accept", "*/*")
                    .header("accept-encoding", "gzip, deflate, br")
                    .header("origin", "https://leonbets.com")
                    .header("referer", "https://leonbets.com/ru-ua/")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
            if (isCookiePresent) {
                requestBuilder.header("cookie", "ABTestSeed=51; referer=https://www.google.com/; x-app-language=ru_UA; firstTheme=DARK; ...");
            }
            HttpRequest request = requestBuilder.GET().build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            String responseBody;
            if ("gzip".equalsIgnoreCase(response.headers().firstValue("content-encoding").orElse(""))) {
                try (InputStream gzipStream = new GZIPInputStream(new ByteArrayInputStream(response.body()))) {
                    responseBody = new String(gzipStream.readAllBytes());
                }
            } else {
                responseBody = new String(response.body());

            }
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
