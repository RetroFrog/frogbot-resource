package io.retrofrog.frogbot.migrations.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import io.retrofrog.frogbot.models.ScanStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeLog(order = "001")
public class ChangelogV001 {

    private static final Logger log = LoggerFactory.getLogger(ChangelogV001.class);

    @ChangeSet(order = "001", id = "addDefaultStrategies", author = "Dan")
    public void addDefaultStrategies(MongoTemplate template, Environment environment) {
        ScanStrategy s = new ScanStrategy("price-breakout");
        s.setMinPriceChangePercent(10f);
        s.setMinVolume(500000f);
        s.setMinPercentChangeDay(1f);

        template.save(s);
    }
}
