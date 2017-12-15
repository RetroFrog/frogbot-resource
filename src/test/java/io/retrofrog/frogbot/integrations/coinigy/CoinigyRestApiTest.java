package io.retrofrog.frogbot.integrations.coinigy;

import io.retrofrog.frogbot.integrations.coinigy.exceptions.CoinigyException;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.*;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class CoinigyRestApiTest {
    private static final Logger log = LoggerFactory.getLogger(CoinigyRestApiTest.class);


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Value("${coinigy.apiKey}")
    private String apiKey;
    @Value("${coinigy.apiSecret}")
    private String apiSecret;

    @Test
    public void invalidAuth() throws CoinigyException {
        thrown.expect(CoinigyException.class);
        thrown.expectMessage(containsString("1055"));

        CoinigyRestApi restApi = new CoinigyRestApi(true, "abc", "123");
        CoinigyResponse<CoinigyExchange> response = restApi.getExchanges();
    }

    @Test
    public void getExchanges() throws CoinigyException {
        CoinigyRestApi restApi = new CoinigyRestApi(true, apiKey, apiSecret);
        CoinigyResponse<CoinigyExchange> response = restApi.getExchanges();
        Assert.assertTrue(response.getData().size() > 0);
    }

    @Test
    public void getMarkets() throws CoinigyException {
        CoinigyRestApi restApi = new CoinigyRestApi(true, apiKey, apiSecret);
        CoinigyResponse<CoinigyMarket> response = restApi.getMarkets("GDAX");
        Assert.assertTrue(response.getData().size() > 0);
    }

    @Test
    public void getAllMarkets() throws CoinigyException {
        CoinigyRestApi restApi = new CoinigyRestApi(true, apiKey, apiSecret);
        CoinigyResponse<CoinigyMarket> response = restApi.getMarkets();
        Assert.assertTrue(response.getData().size() > 0);
    }

    @Test
    public void getActivity() throws CoinigyException {
        CoinigyRestApi restApi = new CoinigyRestApi(true, apiKey, apiSecret);
        CoinigyResponse<CoinigyNotification> response = restApi.getActivity();
    }

    @Test
    public void getPushNotifications() throws CoinigyException {
        //CoinigyRestApi restApi = new CoinigyRestApi(true, apiKey, apiSecret);
        //CoinigyResponse<CoinigyNotification> response = restApi.getPushNotifications();
    }

    @Test
    public void getAccounts() throws CoinigyException {
        CoinigyRestApi restApi = new CoinigyRestApi(true, apiKey, apiSecret);
        CoinigyResponse<CoinigyAccount> response = restApi.getAccounts();
    }
}