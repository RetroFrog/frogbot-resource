package io.retrofrog.frogbot.repositories;

import io.retrofrog.frogbot.models.ExchangeInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeInfoRepository extends MongoRepository<ExchangeInfo, String> {
    List<ExchangeInfo> findByScanEnabled(boolean scanEnabled);

    ExchangeInfo findOneByExchangeId(Integer exchangeId);

    ExchangeInfo findOneByCode(String code);

    ExchangeInfo findOneByName(String name);

    ExchangeInfo findOneByAuthId(Integer authId);
}
