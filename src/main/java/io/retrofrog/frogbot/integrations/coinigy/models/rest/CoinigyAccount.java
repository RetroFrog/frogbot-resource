package io.retrofrog.frogbot.integrations.coinigy.models.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.retrofrog.frogbot.json.NumericBooleanDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinigyAccount implements CoinigyData {
    /*
    "auth_id": "1235",
    "auth_key": "Q6Fpvnw6vBGMKI3uRH1okjJLW7xujEEZ",
    "auth_optional1": "123456",
    "auth_nickname": "My Bitstamp Account",
    "exch_name": "Bitstamp",
    "auth_secret": "************************",
    "auth_updated": "2015-10-16 17:36:35",
    "auth_active": "1",
    "auth_trade": "1",
    "exch_trade_enabled": "1",
    "exch_id": "4"
     */

    @JsonProperty("auth_id")
    private int authId;
    @JsonProperty("auth_key")
    private String key;
    @JsonProperty("auth_optional1")
    private String optional;
    @JsonProperty("auth_nickname")
    private String nickname;
    @JsonProperty("exch_id")
    private int exchangeId;
    @JsonProperty("exch_name")
    private String exchangeName;
    @JsonProperty("auth_secret")
    private String secret;
    @JsonProperty("auth_updated")
    private String updated;
    @JsonProperty("auth_active")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean active;
    @JsonProperty("auth_trade")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean tradeEnabled;
    @JsonProperty("exch_trade_enabled")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean tradeSupported;

    public CoinigyAccount() {

    }

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    public void setTradeEnabled(boolean tradeEnabled) {
        this.tradeEnabled = tradeEnabled;
    }

    public boolean isTradeSupported() {
        return tradeSupported;
    }

    public void setTradeSupported(boolean tradeSupported) {
        this.tradeSupported = tradeSupported;
    }
}
