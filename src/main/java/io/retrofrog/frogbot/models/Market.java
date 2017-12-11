package io.retrofrog.frogbot.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "markets")
@CompoundIndexes({
        @CompoundIndex(name = "market", unique = true, def = "{'asset': 1, 'currency': 1}")
})
public class Market {
    /*@Id
    private String id;
    @DBRef
    private Coin asset;
    @DBRef
    private Currency currency;

    public Market() {}

    public Market(Coin asset, Currency currency) {
        this.asset = asset;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public Coin getAsset() {
        return asset;
    }

    public void setAsset(Coin asset) {
        this.asset = asset;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }*/
}
