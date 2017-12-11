package io.retrofrog.frogbot.integrations;

import com.bugsnag.Bugsnag;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Collections;

@Service
public class Postmark {
    private static final Logger log = LoggerFactory.getLogger(Postmark.class);
    private static final String URL = "https://api.postmarkapp.com/email";

    private RestTemplate restTemplate;

    @Autowired
    private Bugsnag bugsnag;

    @Value("${postmark.enabled:false}")
    private boolean enabled;
    @Value("${postmark.apiKey:}")
    private String apiKey;
    @Value("${postmark.sender:}")
    private String sender;

    public Postmark() {
        restTemplate = new RestTemplate();
    }

    public void send(String to, String subject, String body) {
        if (!enabled) {
            log.debug("Postmark disabled... skipping");
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-Postmark-Server-Token", apiKey);

        PostmarkMessage message = new PostmarkMessage();
        message.setSender(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setBody(body);


        HttpEntity<PostmarkMessage> entity = new HttpEntity<>(message, headers);

        try {
            ObjectMapper mapper = new ObjectMapper();
            log.debug(mapper.writeValueAsString(message));

            restTemplate.postForObject(URL, entity, String.class);
        } catch (Exception ex) {
            log.error("Failed to send message", ex);
            bugsnag.notify(ex);
        }
    }

    private class PostmarkMessage implements Serializable {
        @JsonProperty("From")
        private String sender;
        @JsonProperty("To")
        private String to;
        @JsonProperty("Subject")
        private String subject;
        @JsonProperty("HtmlBody")
        private String htmlBody;
        @JsonProperty("TextBody")
        private String textBody;

        public PostmarkMessage() {}

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getHtmlBody() {
            return htmlBody;
        }

        public void setHtmlBody(String htmlBody) {
            this.htmlBody = htmlBody;
        }

        public String getTextBody() {
            return textBody;
        }

        public void setTextBody(String textBody) {
            this.textBody = textBody;
        }

        public void setBody(String body) {
            this.htmlBody = body;
            this.textBody = body;
        }
    }
}
