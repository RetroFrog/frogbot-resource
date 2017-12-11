package io.retrofrog.frogbot.integrations.coinigy.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class CoinigyRequestInterceptor implements ClientHttpRequestInterceptor {
    private String apiKey;
    private String apiSecret;

    public CoinigyRequestInterceptor(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON);
        headers.add("X-API-KEY", apiKey);
        headers.add("X-API-SECRET", apiSecret);
        return execution.execute(request, body);
    }
}
