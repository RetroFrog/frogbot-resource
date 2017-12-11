package io.retrofrog.frogbot.models;

public class Prospect {
    private String scanStrategyName;
    private String coinName;
    private String symbol;

    private float startPriceUsd;
    private float previousPriceUsd;
    private float currentPriceUsd;

    private float startPriceBtc;
    private float previousPriceBtc;
    private float currentPriceBtc;

    private float startVolumeUsd;
    private float previousVolumeUsd;
    private float currentVolumeUsd;

    private float startPercentChangeHour;
    private float previousPercentChangeHour;
    private float currentPercentChangeHour;

    private float startPercentChangeDay;
    private float previousPercentChangeDay;
    private float currentPercentChangeDay;

    private float startPercentChangeWeek;
    private float previousPercentChangeWeek;
    private float currentPercentChangeWeek;

    private float startPriceChangePercent;
    private float previousPriceChangePercent;
    private float currentPriceChangePercent;

    private float startVolumeChangePercent;
    private float previousVolumeChangePercent;
    private float currentVolumeChangePercent;

    private float confidence;

    public Prospect() {}

    public String getScanStrategyName() {
        return scanStrategyName;
    }

    public void setScanStrategyName(String scanStrategyName) {
        this.scanStrategyName = scanStrategyName;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getStartPriceUsd() {
        return startPriceUsd;
    }

    public void setStartPriceUsd(float startPriceUsd) {
        this.startPriceUsd = startPriceUsd;
        previousPriceUsd = startPriceUsd;
        currentPriceUsd = startPriceUsd;
    }

    public float getPreviousPriceUsd() {
        return previousPriceUsd;
    }

    public void setPreviousPriceUsd(float previousPriceUsd) {
        this.previousPriceUsd = previousPriceUsd;
    }

    public float getCurrentPriceUsd() {
        return currentPriceUsd;
    }

    public void setCurrentPriceUsd(float currentPriceUsd) {
        previousPriceUsd = this.currentPriceUsd;
        this.currentPriceUsd = currentPriceUsd;
    }

    public float getStartPriceBtc() {
        return startPriceBtc;
    }

    public void setStartPriceBtc(float startPriceBtc) {
        this.startPriceBtc = startPriceBtc;
        previousPriceBtc = startPriceBtc;
        currentPriceBtc = startPriceBtc;
    }

    public float getPreviousPriceBtc() {
        return previousPriceBtc;
    }

    public void setPreviousPriceBtc(float previousPriceBtc) {
        this.previousPriceBtc = previousPriceBtc;
    }

    public float getCurrentPriceBtc() {
        return currentPriceBtc;
    }

    public void setCurrentPriceBtc(float currentPriceBtc) {
        previousPriceBtc = this.currentPriceBtc;
        this.currentPriceBtc = currentPriceBtc;
    }

    public float getStartVolumeUsd() {
        return startVolumeUsd;
    }

    public void setStartVolumeUsd(float startVolumeUsd) {
        this.startVolumeUsd = startVolumeUsd;
        previousVolumeUsd = startVolumeUsd;
        currentVolumeUsd = startVolumeUsd;
    }

    public float getPreviousVolumeUsd() {
        return previousVolumeUsd;
    }

    public void setPreviousVolumeUsd(float previousVolumeUsd) {
        this.previousVolumeUsd = previousVolumeUsd;
    }

    public float getCurrentVolumeUsd() {
        return currentVolumeUsd;
    }

    public void setCurrentVolumeUsd(float currentVolumeUsd) {
        previousVolumeUsd = this.currentVolumeUsd;
        this.currentVolumeUsd = currentVolumeUsd;
    }

    public float getStartPercentChangeHour() {
        return startPercentChangeHour;
    }

    public void setStartPercentChangeHour(float startPercentChangeHour) {
        this.startPercentChangeHour = startPercentChangeHour;
        previousPercentChangeHour = startPercentChangeHour;
        currentPercentChangeHour = startPercentChangeHour;
    }

    public float getPreviousPercentChangeHour() {
        return previousPercentChangeHour;
    }

    public void setPreviousPercentChangeHour(float previousPercentChangeHour) {
        this.previousPercentChangeHour = previousPercentChangeHour;
    }

    public float getCurrentPercentChangeHour() {
        return currentPercentChangeHour;
    }

    public void setCurrentPercentChangeHour(float currentPercentChangeHour) {
        previousPercentChangeHour = this.currentPercentChangeHour;
        this.currentPercentChangeHour = currentPercentChangeHour;
    }

    public float getStartPercentChangeDay() {
        return startPercentChangeDay;
    }

    public void setStartPercentChangeDay(float startPercentChangeDay) {
        this.startPercentChangeDay = startPercentChangeDay;
        previousPercentChangeDay = startPercentChangeDay;
        currentPercentChangeDay = startPercentChangeDay;
    }

    public float getPreviousPercentChangeDay() {
        return previousPercentChangeDay;
    }

    public void setPreviousPercentChangeDay(float previousPercentChangeDay) {
        this.previousPercentChangeDay = previousPercentChangeDay;
    }

    public float getCurrentPercentChangeDay() {
        return currentPercentChangeDay;
    }

    public void setCurrentPercentChangeDay(float currentPercentChangeDay) {
        previousPercentChangeDay = this.currentPercentChangeDay;
        this.currentPercentChangeDay = currentPercentChangeDay;
    }

    public float getStartPercentChangeWeek() {
        return startPercentChangeWeek;
    }

    public void setStartPercentChangeWeek(float startPercentChangeWeek) {
        this.startPercentChangeWeek = startPercentChangeWeek;
        previousPercentChangeWeek = startPercentChangeWeek;
        currentPercentChangeWeek = startPercentChangeWeek;
    }

    public float getPreviousPercentChangeWeek() {
        return previousPercentChangeWeek;
    }

    public void setPreviousPercentChangeWeek(float previousPercentChangeWeek) {
        this.previousPercentChangeWeek = previousPercentChangeWeek;
    }

    public float getCurrentPercentChangeWeek() {
        return currentPercentChangeWeek;
    }

    public void setCurrentPercentChangeWeek(float currentPercentChangeWeek) {
        previousPercentChangeWeek = this.currentPercentChangeWeek;
        this.currentPercentChangeWeek = currentPercentChangeWeek;
    }

    public float getStartPriceChangePercent() {
        return startPriceChangePercent;
    }

    public void setStartPriceChangePercent(float startPriceChangePercent) {
        this.startPriceChangePercent = startPriceChangePercent;
        previousPriceChangePercent = startPriceChangePercent;
        currentPriceChangePercent = startPriceChangePercent;
    }

    public float getPreviousPriceChangePercent() {
        return previousPriceChangePercent;
    }

    public void setPreviousPriceChangePercent(float previousPriceChangePercent) {
        this.previousPriceChangePercent = previousPriceChangePercent;
    }

    public float getCurrentPriceChangePercent() {
        return currentPriceChangePercent;
    }

    public void setCurrentPriceChangePercent(float currentPriceChangePercent) {
        previousPriceChangePercent = this.currentPriceChangePercent;
        this.currentPriceChangePercent = currentPriceChangePercent;
    }

    public float getStartVolumeChangePercent() {
        return startVolumeChangePercent;
    }

    public void setStartVolumeChangePercent(float startVolumeChangePercent) {
        this.startVolumeChangePercent = startVolumeChangePercent;
        previousVolumeChangePercent = startVolumeChangePercent;
        currentVolumeChangePercent = startVolumeChangePercent;
    }

    public float getPreviousVolumeChangePercent() {
        return previousVolumeChangePercent;
    }

    public void setPreviousVolumeChangePercent(float previousVolumeChangePercent) {
        this.previousVolumeChangePercent = previousVolumeChangePercent;
    }

    public float getCurrentVolumeChangePercent() {
        return currentVolumeChangePercent;
    }

    public void setCurrentVolumeChangePercent(float currentVolumeChangePercent) {
        previousVolumeChangePercent = this.currentVolumeChangePercent;
        this.currentVolumeChangePercent = currentVolumeChangePercent;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }
}
