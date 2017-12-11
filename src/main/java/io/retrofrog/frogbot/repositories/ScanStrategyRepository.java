package io.retrofrog.frogbot.repositories;

import io.retrofrog.frogbot.models.ScanStrategy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanStrategyRepository extends MongoRepository<ScanStrategy, String> {
    List<ScanStrategy> findByEnabled(boolean enabled);
}
