package io.retrofrog.frogbot.repositories;

import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketDataRepository extends MongoRepository<MarketData, String> {

}
