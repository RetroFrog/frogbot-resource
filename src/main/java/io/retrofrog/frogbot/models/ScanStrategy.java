package io.retrofrog.frogbot.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scan-strategies")
public class ScanStrategy {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private boolean enabled;

    private Float minPercentChangeHour;
    private Float maxPercentChangeHour;
    private Boolean percentChangeHourIncreased;

    private Float minPercentChangeDay;
    private Float maxPercentChangeDay;
    private Boolean percentChangeDayIncreased;

    private Float minPercentChangeWeek;
    private Float maxPercentChangeWeek;
    private Boolean percentChangeWeekIncreased;

    private Float minVolume;
    private Float maxVolume;

    private Float minPriceChangePercent;
    private Float maxPriceChangePercent;

    private Float minVolumeChangePercent;
    private Float maxVolumeChangePercent;

    public ScanStrategy() {
        enabled = true;
        minPercentChangeHour = null;
        maxPercentChangeHour = null;
        percentChangeHourIncreased = null;
        minPercentChangeDay = null;
        maxPercentChangeDay = null;
        percentChangeDayIncreased = null;
        minPercentChangeWeek = null;
        maxPercentChangeWeek = null;
        percentChangeWeekIncreased = null;
        minVolume = null;
        maxVolume = null;
        minPriceChangePercent = null;
        maxPriceChangePercent = null;
        minVolumeChangePercent = null;
        maxVolumeChangePercent = null;
    }

    public ScanStrategy(String name) {
        this();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Float getMinPercentChangeHour() {
        return minPercentChangeHour;
    }

    public void setMinPercentChangeHour(Float minPercentChangeHour) {
        this.minPercentChangeHour = minPercentChangeHour;
    }

    public Float getMaxPercentChangeHour() {
        return maxPercentChangeHour;
    }

    public void setMaxPercentChangeHour(Float maxPercentChangeHour) {
        this.maxPercentChangeHour = maxPercentChangeHour;
    }

    public Boolean getPercentChangeHourIncreased() {
        return percentChangeHourIncreased;
    }

    public void setPercentChangeHourIncreased(Boolean percentChangeHourIncreased) {
        this.percentChangeHourIncreased = percentChangeHourIncreased;
    }

    public Float getMinPercentChangeDay() {
        return minPercentChangeDay;
    }

    public void setMinPercentChangeDay(Float minPercentChangeDay) {
        this.minPercentChangeDay = minPercentChangeDay;
    }

    public Float getMaxPercentChangeDay() {
        return maxPercentChangeDay;
    }

    public void setMaxPercentChangeDay(Float maxPercentChangeDay) {
        this.maxPercentChangeDay = maxPercentChangeDay;
    }

    public Boolean getPercentChangeDayIncreased() {
        return percentChangeDayIncreased;
    }

    public void setPercentChangeDayIncreased(Boolean percentChangeDayIncreased) {
        this.percentChangeDayIncreased = percentChangeDayIncreased;
    }

    public Float getMinPercentChangeWeek() {
        return minPercentChangeWeek;
    }

    public void setMinPercentChangeWeek(Float minPercentChangeWeek) {
        this.minPercentChangeWeek = minPercentChangeWeek;
    }

    public Float getMaxPercentChangeWeek() {
        return maxPercentChangeWeek;
    }

    public void setMaxPercentChangeWeek(Float maxPercentChangeWeek) {
        this.maxPercentChangeWeek = maxPercentChangeWeek;
    }

    public Boolean getPercentChangeWeekIncreased() {
        return percentChangeWeekIncreased;
    }

    public void setPercentChangeWeekIncreased(Boolean percentChangeWeekIncreased) {
        this.percentChangeWeekIncreased = percentChangeWeekIncreased;
    }

    public Float getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(Float minVolume) {
        this.minVolume = minVolume;
    }

    public Float getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(Float maxVolume) {
        this.maxVolume = maxVolume;
    }

    public Float getMinPriceChangePercent() {
        return minPriceChangePercent;
    }

    public void setMinPriceChangePercent(Float minPriceChangePercent) {
        this.minPriceChangePercent = minPriceChangePercent;
    }

    public Float getMaxPriceChangePercent() {
        return maxPriceChangePercent;
    }

    public void setMaxPriceChangePercent(Float maxPriceChangePercent) {
        this.maxPriceChangePercent = maxPriceChangePercent;
    }

    public Float getMinVolumeChangePercent() {
        return minVolumeChangePercent;
    }

    public void setMinVolumeChangePercent(Float minVolumeChangePercent) {
        this.minVolumeChangePercent = minVolumeChangePercent;
    }

    public Float getMaxVolumeChangePercent() {
        return maxVolumeChangePercent;
    }

    public void setMaxVolumeChangePercent(Float maxVolumeChangePercent) {
        this.maxVolumeChangePercent = maxVolumeChangePercent;
    }
}
