package io.retrofrog.frogbot.models;

import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;

import java.util.HashSet;
import java.util.Set;

public class StrategyResult {
    private ScanStrategy strategy;
    private MarketData marketData;
    private float priceChangePercent;
    private float volumeChangePercent;
    private boolean match;
    private Set<String> targetExchanges;
    private int confirmations;

    public StrategyResult() {}

    public StrategyResult(ScanStrategy strategy, MarketData marketData, float priceChangePercent,
                          float volumeChangePercent, boolean match) {
        this.strategy = strategy;
        this.marketData = marketData;
        this.priceChangePercent = priceChangePercent;
        this.volumeChangePercent = volumeChangePercent;
        this.match = match;
        targetExchanges = new HashSet<>();
        confirmations = 0;
    }

    public ScanStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ScanStrategy strategy) {
        this.strategy = strategy;
    }

    public MarketData getMarketData() {
        return marketData;
    }

    public void setMarketData(MarketData marketData) {
        this.marketData = marketData;
    }

    public float getPriceChangePercent() {
        return priceChangePercent;
    }

    public void setPriceChangePercent(float priceChangePercent) {
        this.priceChangePercent = priceChangePercent;
    }

    public float getVolumeChangePercent() {
        return volumeChangePercent;
    }

    public void setVolumeChangePercent(float volumeChangePercent) {
        this.volumeChangePercent = volumeChangePercent;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public Set<String> getTargetExchanges() {
        return targetExchanges;
    }

    public void setTargetExchanges(Set<String> targetExchanges) {
        this.targetExchanges = targetExchanges;
    }

    public String getTargetExchangesString() {
        StringBuilder sb = new StringBuilder();
        for (String s : targetExchanges) {
            sb.append(s).append(", ");
        }
        return sb.toString().substring(0, sb.length() - 2);
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void incrementConfirmations() {
        ++confirmations;
    }
}
