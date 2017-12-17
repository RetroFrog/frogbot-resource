package io.retrofrog.frogbot.services;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InfluxDbService {

    private InfluxDB influxDB;

    private boolean enabled;
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;
    private String retention;

    public InfluxDbService(@Value("${influxdb.enabled:false}") boolean enabled,
                           @Value("${influxdb.host:}") String host,
                           @Value("${influxdb.port:8086}") int port,
                           @Value("${influxdb.username}") String username,
                           @Value("${influxdb.password}") String password,
                           @Value("${influxdb.database:frogbot}") String database,
                           @Value("${influxdb.retention:7d}") String retention) {

        this.enabled = enabled;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.retention = retention;

        if (!enabled)
            return;
        influxDB = InfluxDBFactory.connect(String.format("http://%s:%d", host, port), username, password);
        influxDB.createDatabase(database);
        influxDB.createRetentionPolicy("ticker_rp", database, retention, 0, true);
    }
}
