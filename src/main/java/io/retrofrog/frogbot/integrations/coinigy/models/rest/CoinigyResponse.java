package io.retrofrog.frogbot.integrations.coinigy.models.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinigyResponse<T extends CoinigyData> {
    @JsonProperty("err_num")
    private String errorCode;
    @JsonProperty("err_msg")
    private String errorMessage;
    @JsonProperty("data")
    private List<T> data;
    @JsonProperty("notifications")
    private List<CoinigyNotification> notifications;

    public CoinigyResponse() {
        this.data = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<CoinigyNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<CoinigyNotification> notifications) {
        this.notifications = notifications;
    }

    public boolean isError() {
        return errorCode != null && errorCode.length() > 0;
    }
}
