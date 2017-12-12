package io.retrofrog.frogbot.integrations.coinmarketcap;

import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class CoinMarketCapApiTest {
    @Test
    public void getMarketData() {
        CoinMarketCapApi api = new CoinMarketCapApi();
        List<MarketData> data = api.getMarketData();
        assertTrue(data.size() > 0);
    }
}