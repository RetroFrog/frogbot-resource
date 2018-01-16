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
        s.setMinPercentChangeHour(5f);
        s.setMaxPercentChangeHour(10f);
        s.setPercentChangeHourIncreased(true);
        s.setMaxPercentChangeDay(20f);
        s.setPercentChangeDayIncreased(true);
        s.setMaxPercentChangeWeek(20f);
        s.setPercentChangeWeekIncreased(true);
        s.setMinVolume(500000f);

        template.save(s);
    }
}
