package io.retrofrog.frogbot.integrations.coinigy.models.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinigyMarket implements CoinigyData {
    /*
    "exch_id": "62",
    "exch_name": "Global Digital Asset Exchange",
    "exch_code": "GDAX",
    "mkt_id": "139",
    "mkt_name": "BTC/CAD",
    "exchmkt_id": "7432"
     */

    @JsonProperty("exch_id")
    private int exchangeId;
    @JsonProperty("exch_name")
    private String exchangeName;
    @JsonProperty("exch_code")
    private String exchangeCode;
    @JsonProperty("mkt_id")
    private int marketId;
    @JsonProperty("mkt_name")
    private String name;
    @JsonProperty("exchmkt_id")
    private int exchangeMarketId;

    public CoinigyMarket() {}

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

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExchangeMarketId() {
        return exchangeMarketId;
    }

    public void setExchangeMarketId(int exchangeMarketId) {
        this.exchangeMarketId = exchangeMarketId;
    }

    public String getAsset() {
        return name.split("\\/")[0];
    }
}
