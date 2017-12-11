package io.retrofrog.frogbot.integrations.coinigy.http;

import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class CoinigyRestTemplate extends RestTemplate {

    public CoinigyRestTemplate(String apiKey, String apiSecret) {
        setInterceptors(Collections.singletonList(new CoinigyRequestInterceptor(apiKey, apiSecret)));
    }

}
