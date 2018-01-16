package io.retrofrog.frogbot.integrations.coinmarketcap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "market-data")
public class MarketData {

    @Id
    private String id;

    @JsonProperty("id")
    private String coinId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("price_usd")
    private float priceUsd;

    @JsonProperty("price_btc")
    private float priceBtc;

    @JsonProperty("24h_volume_usd")
    private float dayVolumeUsd;

    @JsonProperty("market_cap_usd")
    private float marketCapUsd;

    @JsonProperty("available_supply")
    private float availableSupply;

    @JsonProperty("total_supply")
    private float totalSupply;

    @JsonProperty("percent_change_1h")
    private float percentChangeHour;

    @JsonProperty("percent_change_24h")
    private float percentChangeDay;

    @JsonProperty("percent_change_7d")
    private float percentChangeWeek;

    @JsonProperty("last_updated")
    private long lastUpdated;

    public MarketData() {}

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(float priceUsd) {
        this.priceUsd = priceUsd;
    }

    public float getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(float priceBtc) {
        this.priceBtc = priceBtc;
    }

    public float getDayVolumeUsd() {
        return dayVolumeUsd;
    }

    public void setDayVolumeUsd(float dayVolumeUsd) {
        this.dayVolumeUsd = dayVolumeUsd;
    }

    public float getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(float marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public float getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(float availableSupply) {
        this.availableSupply = availableSupply;
    }

    public float getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(float totalSupply) {
        this.totalSupply = totalSupply;
    }

    public float getPercentChangeHour() {
        return percentChangeHour;
    }

    public void setPercentChangeHour(float percentChangeHour) {
        this.percentChangeHour = percentChangeHour;
    }

    public float getPercentChangeDay() {
        return percentChangeDay;
    }

    public void setPercentChangeDay(float percentChangeDay) {
        this.percentChangeDay = percentChangeDay;
    }

    public float getPercentChangeWeek() {
        return percentChangeWeek;
    }

    public void setPercentChangeWeek(float percentChangeWeek) {
        this.percentChangeWeek = percentChangeWeek;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
