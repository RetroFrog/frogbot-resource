package io.retrofrog.frogbot.integrations.coinigy;

import io.retrofrog.frogbot.integrations.coinigy.exceptions.CoinigyException;
import io.retrofrog.frogbot.integrations.coinigy.http.CoinigyRestTemplate;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyExchange;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyMarket;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyNotification;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CoinigyRestApi {
    private static final Logger log = LoggerFactory.getLogger(CoinigyRestApi.class);
    private static final String URL = "https://api.coinigy.com/api/v1/";


    private CoinigyRestTemplate restTemplate;
    private boolean enabled;

    public CoinigyRestApi(@Value("${coinigy.enabled:false}") boolean enabled,
                          @Value("${coinigy.apiKey:}") String apiKey,
                          @Value("${coinigy.apiSecret:}") String apiSecret) {
        this.enabled = enabled;
        if (!enabled) {
            log.info("Coinigy is not enabled...");
            return;
        }

        this.restTemplate = new CoinigyRestTemplate(apiKey, apiSecret);
    }

    /**
     * Get list of recent account activity
     * This appears to just be recent notifications
     *
     * @return response containing list of recent notifications
     * @throws CoinigyException
     */
    public CoinigyResponse<CoinigyNotification> getActivity() throws CoinigyException {
        if (!enabled)
            return null;

        ResponseEntity<CoinigyResponse<CoinigyNotification>> response = restTemplate.exchange(
                URL + "activity", HttpMethod.POST, null,
                new ParameterizedTypeReference<CoinigyResponse<CoinigyNotification>>() {
                });
        CoinigyResponse<CoinigyNotification> cr = response.getBody();

        if (cr.isError())
            throw new CoinigyException(cr.getErrorCode(), cr.getErrorMessage());

        return response.getBody();
    }

    /**
     * Get list of unseen notifications
     * ** This appears to be missing
     *
     * @return response containing list of unseen notifications
     * @throws CoinigyException
     */
    public CoinigyResponse<CoinigyNotification> getPushNotifications() throws CoinigyException {
        if (!enabled)
            return null;

        ResponseEntity<CoinigyResponse<CoinigyNotification>> response = restTemplate.exchange(
                URL + "pushNotifications", HttpMethod.POST, null,
                new ParameterizedTypeReference<CoinigyResponse<CoinigyNotification>>() {
                });
        CoinigyResponse<CoinigyNotification> cr = response.getBody();

        if (cr.isError())
            throw new CoinigyException(cr.getErrorCode(), cr.getErrorMessage());

        return response.getBody();
    }

    /**
     * Get list of exchanges
     * @return response containing list of exchanges
     * @throws CoinigyException
     */
    public CoinigyResponse<CoinigyExchange> getExchanges() throws CoinigyException {
        if (!enabled)
            return null;

        ResponseEntity<CoinigyResponse<CoinigyExchange>> response = restTemplate.exchange(
                URL + "exchanges", HttpMethod.POST, null,
                new ParameterizedTypeReference<CoinigyResponse<CoinigyExchange>>() {
                });
        CoinigyResponse<CoinigyExchange> cr = response.getBody();

        if (cr.isError())
            throw new CoinigyException(cr.getErrorCode(), cr.getErrorMessage());

        return response.getBody();
    }

    /**
     * Get list of markets for given exchange
     * @param exchangeCode code (i.e. GDAX) of exchange
     * @return response containing list of markets
     * @throws CoinigyException
     */
    public CoinigyResponse<CoinigyMarket> getMarkets(String exchangeCode) throws CoinigyException {
        if (!enabled)
            return null;

        JSONObject o = new JSONObject();
        o.put("exchange_code", exchangeCode);
        HttpEntity<String> request = new HttpEntity<>(o.toString());

        ResponseEntity<CoinigyResponse<CoinigyMarket>> response = restTemplate.exchange(
                URL + "markets", HttpMethod.POST, request,
                new ParameterizedTypeReference<CoinigyResponse<CoinigyMarket>>() {
                });
        CoinigyResponse<CoinigyMarket> cr = response.getBody();

        if (cr.isError())
            throw new CoinigyException(cr.getErrorCode(), cr.getErrorMessage());

        return response.getBody();
    }

    /**
     * Get list of markets for all exchanges
     * @return response containing list of markets
     * @throws CoinigyException
     */
    public CoinigyResponse<CoinigyMarket> getMarkets() throws CoinigyException {
        return getMarkets(null);
    }
}