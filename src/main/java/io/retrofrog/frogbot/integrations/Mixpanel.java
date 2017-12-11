package io.retrofrog.frogbot.integrations;

import com.bugsnag.Bugsnag;
import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Mixpanel {
    private static final Logger log = LoggerFactory.getLogger(Mixpanel.class);

    @Autowired
    private Bugsnag bugsnag;

    @Value("${mixpanel.enabled:false}")
    private boolean enabled;

    @Value("${mixpanel.apiKey:}")
    private String apiKey;

    private MixpanelAPI mixpanel;

    public Mixpanel() {
        mixpanel = new MixpanelAPI();
    }

    public void send(String distinctId, String eventName, JSONObject properties) {
        if (!enabled) {
            log.debug("Mixpanel disabled... skipping");
            return;
        }

        MessageBuilder message = new MessageBuilder(apiKey);

        ClientDelivery delivery = new ClientDelivery();
        delivery.addMessage(message.event(distinctId, eventName, properties));

        try {
            log.debug("Sending to Mixpanel");
            //mixpanel.deliver(delivery);
            mixpanel.sendMessage(message.event(distinctId, eventName, properties));
        } catch (IOException ex) {
            log.error("Failed to send to Mixpanel", ex);
            bugsnag.notify(ex);
        }

    }
}
