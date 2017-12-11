package io.retrofrog.frogbot.integrations.coinmarketcap;

import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CoinMarketCap {

    private static final Logger log = LoggerFactory.getLogger(CoinMarketCap.class);

    public List<MarketData> getMarketData() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<MarketData>> response = restTemplate.exchange(
                "https://api.coinmarketcap.com/v1/ticker/?limit=0", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MarketData>>(){
                });

        return response.getBody();
    }
}
