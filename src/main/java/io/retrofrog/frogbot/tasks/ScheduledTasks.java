package io.retrofrog.frogbot.tasks;

import com.bugsnag.Bugsnag;
import io.retrofrog.frogbot.engines.Scanner;
import io.retrofrog.frogbot.integrations.coinigy.Coinigy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private Bugsnag bugsnag;

    @Autowired
    private Scanner scanner;

    @Autowired
    private Coinigy coinigy;

    @Scheduled(cron = "0 */5 * * * ?")
    public void scan() {
        try {
            scanner.scanCoinMarketCap();
        } catch (Throwable t) {
            bugsnag.notify(t);
            throw t;
        }
    }
}