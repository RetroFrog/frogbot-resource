package io.retrofrog.frogbot.models;

import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyExchange;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "exchanges")
/*@CompoundIndexes({
        @CompoundIndex(name = "market", unique = true, def = "{'asset': 1, 'currency': 1}")
})*/
public class ExchangeInfo {
    @Id
    private String id;

    @Indexed(unique = true)
    private Integer exchangeId;
    @Indexed(unique = true)
    private String name;
    @Indexed(unique = true)
    private String code;
    private Float fee;
    private boolean tradeEnabled;
    private boolean balanceEnabled;
    private String url;
    private Integer authId;
    private boolean scanEnabled;

    private Set<String> currencies;

    public ExchangeInfo() {
        currencies = new HashSet<>();
        scanEnabled = false;
    }

    public ExchangeInfo(CoinigyExchange coinigyExchange) {
        this();
        exchangeId = coinigyExchange.getExchangeId();
        name = coinigyExchange.getName();
        code = coinigyExchange.getCode();
        fee = coinigyExchange.getFee();
        tradeEnabled = coinigyExchange.isTradeEnabled();
        balanceEnabled = coinigyExchange.isBalanceEnabled();
        url = coinigyExchange.getUrl();
    }

    public String getId() {
        return id;
    }

    public Integer getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Integer exchangeId) {
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

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
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

    public boolean isScanEnabled() {
        return scanEnabled;
    }

    public void setScanEnabled(boolean scanEnabled) {
        this.scanEnabled = scanEnabled;
    }

    public Set<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<String> currencies) {
        this.currencies = currencies;
    }

    public void addCurrency(String currency) {
        currencies.add(currency);
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public boolean hasCurrency(String symbol) {
        return this.currencies.contains(symbol);
    }
}
