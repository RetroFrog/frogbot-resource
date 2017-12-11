package io.retrofrog.frogbot.models;

import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;

public class StrategyResult {
    private ScanStrategy strategy;
    private MarketData marketData;
    private float priceChangePercent;
    private float volumeChangePercent;
    private boolean match;

    public StrategyResult() {}

    public StrategyResult(ScanStrategy strategy, MarketData marketData, float priceChangePercent,
                          float volumeChangePercent, boolean match) {
        this.strategy = strategy;
        this.marketData = marketData;
        this.priceChangePercent = priceChangePercent;
        this.volumeChangePercent = volumeChangePercent;
        this.match = match;
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
}
