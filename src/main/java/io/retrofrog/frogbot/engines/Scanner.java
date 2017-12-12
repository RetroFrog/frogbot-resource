package io.retrofrog.frogbot.engines;

import io.retrofrog.frogbot.integrations.Mixpanel;
import io.retrofrog.frogbot.integrations.Postmark;
import io.retrofrog.frogbot.integrations.Pushover;
import io.retrofrog.frogbot.integrations.coinmarketcap.CoinMarketCapApi;
import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;
import io.retrofrog.frogbot.integrations.irc.Irc;
import io.retrofrog.frogbot.integrations.irc.utils.IrcString;
import io.retrofrog.frogbot.integrations.irc.utils.IrcStyle;
import io.retrofrog.frogbot.models.Prospect;
import io.retrofrog.frogbot.models.ScanStrategy;
import io.retrofrog.frogbot.models.StrategyResult;
import io.retrofrog.frogbot.repositories.ScanStrategyRepository;
import io.retrofrog.frogbot.utils.ColorString;
import io.retrofrog.frogbot.utils.Colors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class Scanner {
    private static final Logger log = LoggerFactory.getLogger(Scanner.class);
    private static final DecimalFormat f = new DecimalFormat("##.00");

    @Autowired
    private CoinMarketCapApi coinMarketCapApi;
    @Autowired
    private Pushover pushover;
    @Autowired
    private Postmark postmark;
    @Autowired
    private Mixpanel mixpanel;
    @Autowired
    private Irc irc;

    @Autowired
    private ScanStrategyRepository scanStrategyRepository;

    private List<MarketData> previousData;

    public Scanner() {
        previousData = new ArrayList<>();
    }

    public void scanCoinMarketCap() {
        List<MarketData> currentData = coinMarketCapApi.getMarketData();
        List<StrategyResult> scanResults = new ArrayList<>();

        if (previousData.size() == 0) {
            log.info("No previous data...");
            previousData = currentData;
            return;
        }

        for (MarketData c : currentData) {
            for (MarketData p : previousData) {
                if (c.getCoinId().equals(p.getCoinId()) && c.getLastUpdated() != p.getLastUpdated()) {

                    float priceChange = getPercentChange(c.getPriceUsd(), p.getPriceUsd());
                    float volumeChange = getPercentChange(c.getDayVolumeUsd(), p.getDayVolumeUsd());


                    for (ScanStrategy s : scanStrategyRepository.findByEnabled(true)) {
                        StrategyResult res = runStrategy(s, c, p);
                        if (res.isMatch()) {
                            scanResults.add(res);
                            log.info(String.format("%s (%s) $%s(%s%%) H:%s%% D:%s%% W:%s%% V:$%s(%s%%)",
                                    new ColorString(c.getName(), Colors.Cyan),
                                    new ColorString(c.getSymbol(), Colors.Magenta),
                                    new ColorString(c.getPriceUsd(), Colors.Yellow),
                                    new ColorString(f.format(priceChange), (priceChange >= 0 ? Colors.Green : Colors.Red)),
                                    new ColorString(c.getPercentChangeHour(), (c.getPercentChangeHour() >= 0 ? Colors.Green : Colors.Red)),
                                    new ColorString(c.getPercentChangeDay(), (c.getPercentChangeDay() >= 0 ? Colors.Green : Colors.Red)),
                                    new ColorString(c.getPercentChangeWeek(), (c.getPercentChangeWeek() >= 0 ? Colors.Green : Colors.Red)),
                                    new ColorString(c.getDayVolumeUsd(), Colors.Yellow),
                                    new ColorString(f.format(volumeChange), (volumeChange >= 0 ? Colors.Green : Colors.Red))
                            ));

                            // IRC
                            irc.send(String.format("%s (%s) $%s(%s%%) H:%s%% D:%s%% W:%s%% V:$%s(%s%%)",
                                    new IrcString(c.getName(), IrcStyle.Cyan),
                                    new IrcString(c.getSymbol(), IrcStyle.Pink),
                                    new IrcString(c.getPriceUsd(), IrcStyle.Yellow),
                                    new IrcString(f.format(priceChange), (priceChange >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                                    new IrcString(c.getPercentChangeHour(), (c.getPercentChangeHour() >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                                    new IrcString(c.getPercentChangeDay(), (c.getPercentChangeDay() >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                                    new IrcString(c.getPercentChangeWeek(), (c.getPercentChangeWeek() >= 0 ? IrcStyle.Green : IrcStyle.Red)),
                                    new IrcString(c.getDayVolumeUsd(), IrcStyle.Yellow),
                                    new IrcString(f.format(volumeChange), (volumeChange >= 0 ? IrcStyle.Green : IrcStyle.Red))
                            ));
                        }
                    }

                    break;
                }
            }
        }

        sendNotifications(scanResults);

    }

    private Prospect buildProspect(Prospect p, MarketData current, MarketData previous, float priceChange, float volumeChange) {
        if (p == null) {
            p = new Prospect();
            p.setCoinName(current.getName());
            p.setSymbol(current.getSymbol());

            p.setStartPriceUsd(previous.getPriceUsd());
            p.setStartPriceBtc(previous.getPriceBtc());
            p.setStartVolumeUsd(previous.getDayVolumeUsd());
            p.setStartPercentChangeHour(previous.getPercentChangeHour());
            p.setStartPercentChangeDay(previous.getPercentChangeDay());
            p.setStartPercentChangeWeek(previous.getPercentChangeWeek());
            p.setStartPriceChangePercent(0);
            p.setStartVolumeChangePercent(0);
        }
        p.setCurrentPriceUsd(current.getPriceUsd());
        p.setCurrentPriceBtc(current.getPriceBtc());
        p.setCurrentVolumeUsd(current.getDayVolumeUsd());
        p.setCurrentPercentChangeHour(current.getPercentChangeHour());
        p.setCurrentPercentChangeDay(current.getPercentChangeDay());
        p.setCurrentPercentChangeWeek(current.getPercentChangeWeek());
        p.setCurrentPriceChangePercent(priceChange);
        p.setCurrentVolumeChangePercent(volumeChange);

        return p;
    }


    private StrategyResult runStrategy(ScanStrategy s, MarketData c, MarketData p) {
        float priceChange = getPercentChange(c.getPriceUsd(), p.getPriceUsd());
        float volumeChange = getPercentChange(c.getDayVolumeUsd(), p.getDayVolumeUsd());

        StrategyResult result = new StrategyResult(s, c, priceChange, volumeChange, false);

        if (s.getMinPercentChangeHour() != null && c.getPercentChangeHour() < s.getMinPercentChangeHour())
            return result;
        if (s.getMaxPercentChangeHour() != null && c.getPercentChangeHour() > s.getMaxPercentChangeHour())
            return result;
        if (s.getMinPercentChangeDay() != null && c.getPercentChangeDay() < s.getMinPercentChangeDay())
            return result;
        if (s.getMaxPercentChangeDay() != null && c.getPercentChangeDay() > s.getMaxPercentChangeDay())
            return result;
        if (s.getMinPercentChangeWeek() != null && c.getPercentChangeWeek() < s.getMinPercentChangeWeek())
            return result;
        if (s.getMaxPercentChangeWeek() != null && c.getPercentChangeWeek() > s.getMaxPercentChangeWeek())
            return result;
        if (s.getMinVolume() != null && c.getDayVolumeUsd() < s.getMinVolume())
            return result;
        if (s.getMaxVolume() != null && c.getDayVolumeUsd() > s.getMaxVolume())
            return result;
        if (s.getMinPriceChangePercent() != null && priceChange < s.getMinPriceChangePercent())
            return result;
        if (s.getMaxPriceChangePercent() != null && priceChange > s.getMaxPriceChangePercent())
            return result;
        if (s.getMinVolumeChangePercent() != null && volumeChange < s.getMinVolumeChangePercent())
            return result;
        if (s.getMaxVolumeChangePercent() != null && volumeChange > s.getMaxVolumeChangePercent())
            return result;

        result.setMatch(true);
        return result;
    }

    private void sendNotifications(List<StrategyResult> scanResults) {
        if (scanResults.size() == 0)
            return;
        mixpanel.send("scanResults", "Scan Results", null);

        StringBuilder sb = new StringBuilder();
        for (StrategyResult r: scanResults) {
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

    public float getPercentChange(float newValue, float oldValue) {
        return ((newValue - oldValue) / oldValue) * 100;
    }
}
