package io.retrofrog.frogbot.tasks;

import com.bugsnag.Bugsnag;
import io.retrofrog.frogbot.engines.Scanner;
import io.retrofrog.frogbot.services.CoinigyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private AtomicBoolean exchangeDataInitialized = new AtomicBoolean(false);

    @Autowired
    private Bugsnag bugsnag;

    @Autowired
    private CoinigyService coinigyService;
    @Autowired
    private Scanner scanner;

    @Scheduled(cron = "0 */30 * * * ?")
    public void updateExchangeData() {
        coinigyService.updateExchangeData();
        exchangeDataInitialized.set(true);
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void scan() {
        if (!exchangeDataInitialized.get())
            updateExchangeData();

        try {
            scanner.scanCoinMarketCap();
        } catch (Throwable t) {
            bugsnag.notify(t);
            throw t;
        }
    }
}