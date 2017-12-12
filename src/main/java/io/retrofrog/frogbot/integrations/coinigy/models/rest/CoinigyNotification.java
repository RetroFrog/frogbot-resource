package io.retrofrog.frogbot.integrations.coinigy.models.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinigyNotification implements CoinigyData {
    /*
    "notification_vars": "BTC/USD,GDAX,690.99,",
    "notification_title_vars": "06-25-2016 13:43:13",
    "notification_time_added": "2016-06-25 13:43:13",
    "notification_type_title": "Price Alert %s",
    "notification_type_message": "%1$s [%2$s] @ %3$s%4$s",
    "notification_style": "info"
     */

    @JsonProperty("notification_vars")
    private String messageVars;
    @JsonProperty("notification_title_vars")
    private String titleVars;
    @JsonProperty("notification_time_added")
    private String timeAdded;
    @JsonProperty("notification_type_title")
    private String title;
    @JsonProperty("notification_type_message")
    private String message;
    @JsonProperty("notification_style")
    private String style;

    public CoinigyNotification() {
    }

    public String getMessageVars() {
        return messageVars;
    }

    public void setMessageVars(String messageVars) {
        this.messageVars = messageVars;
    }

    public String getTitleVars() {
        return titleVars;
    }

    public void setTitleVars(String titleVars) {
        this.titleVars = titleVars;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
