package io.retrofrog.frogbot.integrations;

import com.bugsnag.Bugsnag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Service
public class Pushover {
    private static final Logger log = LoggerFactory.getLogger(Pushover.class);
    private static final String URL = "https://api.pushover.net/1/messages.json";

    @Autowired
    private Bugsnag bugsnag;

    @Value("${pushover.enabled:false}")
    private boolean enabled;
    @Value("${pushover.apiKey:}")
    private String apiKey;
    @Value("${pushover.userKey:}")
    private String userKey;

    private RestTemplate restTemplate;

    public Pushover() {
        restTemplate = new RestTemplate();
    }

    public void send(String title, String message) {
        if (!enabled) {
            log.debug("Pushover disabled... skipping");
            return;
        }
        PushoverMessage pm = new PushoverMessage();
        pm.setToken(apiKey);
        pm.setUser(userKey);
        pm.setTitle(title);
        pm.setMessage(message);

        try {
            restTemplate.postForObject(URL, pm, String.class);
        } catch (HttpClientErrorException ex) {
            log.error("Failed to push message", ex);
            bugsnag.notify(ex);
        }
    }

    private class PushoverMessage implements Serializable {
        private String token;
        private String user;
        private String message;
        private String title;

        public PushoverMessage(){}

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
