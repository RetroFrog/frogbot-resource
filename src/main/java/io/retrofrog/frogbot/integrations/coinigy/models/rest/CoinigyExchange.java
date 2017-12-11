package io.retrofrog.frogbot.integrations.coinigy.models.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.retrofrog.frogbot.json.NumericBooleanDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinigyExchange implements CoinigyData {
    /*
    "exch_id": "2",
    "exch_name": "BTC-e",
    "exch_code": "BTCE",
    "exch_fee": "0.003",
    "exch_trade_enabled": "1",
    "exch_balance_enabled": "1",
    "exch_url": "https://btc-e.com/"
     */

    @JsonProperty("exch_id")
    private int exchangeId;
    @JsonProperty("exch_name")
    private String name;
    @JsonProperty("exch_code")
    private String code;
    @JsonProperty("exch_fee")
    private float fee;
    @JsonProperty("exch_trade_enabled")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean tradeEnabled;
    @JsonProperty("exch_balance_enabled")
    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean balanceEnabled;
    @JsonProperty("exch_url")
    private String url;

    public CoinigyExchange() {}


    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public boolean isTradeEnabled() {
        return tradeEnabled;
    }

    public void setTradeEnabled(boolean tradeEnabled) {
        this.tradeEnabled = tradeEnabled;
    }

    public boolean isBalanceEnabled() {
        return balanceEnabled;
    }

    public void setBalanceEnabled(boolean balanceEnabled) {
        this.balanceEnabled = balanceEnabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
