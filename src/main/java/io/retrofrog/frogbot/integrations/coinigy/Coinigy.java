package io.retrofrog.frogbot.integrations.coinigy;

import com.bugsnag.Bugsnag;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import io.github.sac.BasicListener;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.github.sac.Socket;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class Coinigy {
    private static final Logger log = LoggerFactory.getLogger(Coinigy.class);
    private static final String WS_URL = "wss://sc-02.coinigy.com/socketcluster/";

    @Autowired
    private Bugsnag bugsnag;

    private RestTemplate restTemplate;
    private Socket socket;

    private boolean enabled;
    private String apiKey;
    private String apiSecret;
    private String privateChannelId;

    public Coinigy(@Value("${coinigy.enabled}") boolean enabled, @Value("${coinigy.apiKey:}") String apiKey,
                   @Value("${coinigy.apiSecret:}") String apiSecret,
                   @Value("${coinigy.privateChannelId}") String privateChannelId) {

        restTemplate = new RestTemplate();
        this.enabled = enabled;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.privateChannelId = privateChannelId;

        if (!enabled) {
            log.debug("Coinigy not enabled...");
            return;
        }

        log.info("Initializing Coinigy");

        this.restTemplate = new RestTemplate();
        initializeWebSockets();
    }

    private void initializeWebSockets() {
        socket = new Socket(WS_URL);
        socket.disableLogging();
        socket.setListener(new CoinigyListener(apiKey, apiSecret, privateChannelId));

        socket.onSubscribe(privateChannelId, (name, data) -> {
            JSONObject d = (JSONObject) data;
            //log.debug(d.toString(4));
        });

        /*socket.onSubscribe("TRADE-GDAX--ETH--BTC", (name, data) -> {
            JSONObject object= (JSONObject) data;

            //{
            //    "quantity": 12.72479574,
            //    "channel": "TRADE-GDAX--ETH--BTC",
            //    "time_local": "2017-12-09 09:50:15",
            //    "label": "ETH/BTC",
            //    "type": "SELL",
            //    "marketid": 0,
            //    "exchId": 0,
            //    "total": 0.4083386953,
            //    "market_history_id": 140100043844,
            //    "price": 0.03209,
            //    "exchange": "GDAX",
            //    "time": "2017-12-09T09:50:15",
            //    "tradeid": "767196840",
            //    "timestamp": "2017-12-09T09:50:15Z"
            //}

            try {
                log.debug("Got message: " + object.toString(4));
            } catch (JSONException e) {
                log.error("Channel error", e);
            }
        });*/

        socket.connect();
    }

    private class CoinigyListener implements BasicListener {

        private String apiKey;
        private String apiSecret;
        private String privateChannelId;

        public CoinigyListener(String apiKey, String apiSecret, String privateChannelId) {
            this.apiKey = apiKey;
            this.apiSecret = apiSecret;
            this.privateChannelId = privateChannelId;
        }

        @Override
        public void onConnected(Socket socket, Map<String, List<String>> headers) {
            log.info("Connected to Coinigy");
        }

        @Override
        public void onDisconnected(Socket socket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
                                   boolean closedByServer) {
            log.info("Disconnected from end-point");

        }

        @Override
        public void onConnectError(Socket socket, WebSocketException exception) {
            log.error("Got connect error ", exception);
            bugsnag.notify(exception);
        }

        @Override
        public void onAuthentication(Socket socket, Boolean status) {
            if (status) {
                log.info("socket is authenticated");
            } else {
                log.info("Authentication is required (optional)");
                JSONObject auth = new JSONObject();
                auth.put("apiKey", apiKey);
                auth.put("apiSecret", apiSecret);
                socket.emit("auth", auth, (event, error, data) -> {
                    if (error != null) {
                        log.error("Coinigy auth error", error);
                        return;
                    }
                    //socket.createChannel("TRADE-GDAX--ETH--BTC").subscribe();
                    socket.createChannel(privateChannelId).subscribe();
                });
            }
        }

        @Override
        public void onSetAuthToken(String token, Socket socket) {
            log.info("Got auth token :: "+ token );
            socket.setAuthToken(token);
        }
    }
}
