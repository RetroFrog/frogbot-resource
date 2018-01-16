package io.retrofrog.frogbot.services;

import com.bugsnag.Bugsnag;
import io.retrofrog.frogbot.integrations.Mixpanel;
import io.retrofrog.frogbot.integrations.Postmark;
import io.retrofrog.frogbot.integrations.Pushover;
import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;
import io.retrofrog.frogbot.integrations.irc.Irc;
import io.retrofrog.frogbot.integrations.irc.utils.IrcString;
import io.retrofrog.frogbot.integrations.irc.utils.IrcStyle;
import io.retrofrog.frogbot.models.StrategyResult;
import io.retrofrog.frogbot.utils.ColorString;
import io.retrofrog.frogbot.utils.Colors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private static final DecimalFormat f = new DecimalFormat("##.00");

    @Autowired
    private Pushover pushover;
    @Autowired
    private Postmark postmark;
    @Autowired
    private Mixpanel mixpanel;
    @Autowired
    private Irc irc;
    @Autowired
    private Bugsnag bugsnag;

    public void notifyScanResults(List<StrategyResult> scanResults) {
        if (scanResults.size() == 0)
            return;

        sendBulkScanResults(scanResults);
        sendScanResults(scanResults);
    }

    /**
     * Sends a single condensed message to services that are rate limited (i.e. Pushover)
     *
     * @param scanResults
     */
    private void sendBulkScanResults(List<StrategyResult> scanResults) {
        mixpanel.send("scanResults", "Scan Results", null);

        StringBuilder sb = new StringBuilder();
        for (StrategyResult r : scanResults) {
            mixpanel.send("strategyResult", "Strategy Result", null);

            MarketData d = r.getMarketData();
            sb.append(String.format("[%s (%s) $%f/%f%% H:%f%% D:%f%% W:%f%%]",
                    d.getName(),
                    d.getSymbol(),
                    d.getPriceUsd(),
                    r.getPriceChangePercent(),
                    d.getPercentChangeHour(),
                    d.getPercentChangeDay(),
                    d.getPercentChangeWeek()
            )).append("\n");
        }
        pushover.send("PROSPECTS", sb.toString());
        postmark.send("dan.armstrong@protonmail.com", "PROSPECTS", sb.toString());
    }

    /**
     * Sends multiple messages to services (i.e. console and IRC)
     *
     * @param scanResults
     */
    private void sendScanResults(List<StrategyResult> scanResults) {
        log.info("\n+--------------------+\n|    Scan Results    |\n+--------------------+");

        irc.send("+--------------------+");
        irc.send("|    Scan Results    |");
        irc.send("+--------------------+");

        for (StrategyResult res : scanResults) {
            MarketData c = res.getMarketData();
            log.info(String.format("%s:  %s (%s) $%s(%s%%) H:%s%% D:%s%% W:%s%% V:$%s(%s%%)",
                    new ColorString(res.getTargetExchangesString(), Colors.Bold, Colors.Magenta),
                    new ColorString(c.getName(), Colors.Cyan),
                    new ColorString(c.getSymbol(), Colors.Magenta),
                    new ColorString(c.getPriceUsd(), Colors.Yellow),
                    new ColorString(f.format(res.getPriceChangePercent()),
                            (res.getPriceChangePercent() >= 0 ? Colors.Green : Colors.Red)),
                    new ColorString(c.getPercentChangeHour(), (c.getPercentChangeHour() >= 0 ? Colors.Green : Colors.Red)),
                    new ColorString(c.getPercentChangeDay(), (c.getPercentChangeDay() >= 0 ? Colors.Green : Colors.Red)),
                    new ColorString(c.getPercentChangeWeek(), (c.getPercentChangeWeek() >= 0 ? Colors.Green : Colors.Red)),
                    new ColorString(c.getDayVolumeUsd(), Colors.Yellow),
                    new ColorString(f.format(res.getVolumeChangePercent()),
                            (res.getVolumeChangePercent() >= 0 ? Colors.Green : Colors.Red))
            ));

            // IRC
            irc.send(String.format("%s: %s (%s) $%s(%s%%) H:%s%% D:%s%% W:%s%% V:$%s(%s%%)",
                    new IrcString(res.getTargetExchangesString(), IrcStyle.Bold, IrcStyle.Purple),
                    new IrcString(c.getName(), IrcStyle.Cyan),
                    new IrcString(c.getSymbol(), IrcStyle.Pink),
                    new IrcString(c.getPriceUsd(), IrcStyle.Yellow),
                    new IrcString(f.format(res.getPriceChangePercent()),
                            (res.getPriceChangePercent() >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                    new IrcString(c.getPercentChangeHour(), (c.getPercentChangeHour() >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                    new IrcString(c.getPercentChangeDay(), (c.getPercentChangeDay() >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                    new IrcString(c.getPercentChangeWeek(), (c.getPercentChangeWeek() >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                    new IrcString(c.getDayVolumeUsd(), IrcStyle.Yellow),
                    new IrcString(f.format(res.getVolumeChangePercent()),
                            (res.getVolumeChangePercent() >= 0 ? IrcStyle.Green : IrcStyle.Red))
            ));
        }
    }

}
