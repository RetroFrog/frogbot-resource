package io.retrofrog.frogbot.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient(@Value("${spring.data.mongodb.host:localhost}") String host,
                                   @Value("${spring.data.mongodb.port:27017}") int port) {
        return new MongoClient(host, port);
    }

    @Bean
    public MongoTemplate mongoTemplate(@Value("${spring.data.mongodb.host:localhost}") String host,
                                       @Value("${spring.data.mongodb.port:27017}") int port,
                                       @Value("${spring.data.mongodb.database}") String database) {
        return new MongoTemplate(mongoClient(host, port), database);
    }
}
