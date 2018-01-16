package io.retrofrog.frogbot.engines;

import com.bugsnag.Bugsnag;
import io.retrofrog.frogbot.integrations.coinmarketcap.CoinMarketCapApi;
import io.retrofrog.frogbot.integrations.coinmarketcap.models.MarketData;
import io.retrofrog.frogbot.models.ExchangeInfo;
import io.retrofrog.frogbot.models.Prospect;
import io.retrofrog.frogbot.models.ScanStrategy;
import io.retrofrog.frogbot.models.StrategyResult;
import io.retrofrog.frogbot.repositories.ExchangeInfoRepository;
import io.retrofrog.frogbot.repositories.MarketDataRepository;
import io.retrofrog.frogbot.repositories.ScanStrategyRepository;
import io.retrofrog.frogbot.services.NotificationService;
import io.retrofrog.frogbot.utils.ColorString;
import io.retrofrog.frogbot.utils.Colors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class Scanner {
    private static final Logger log = LoggerFactory.getLogger(Scanner.class);

    @Value("${aggregate:false}")
    private boolean aggregate;
    @Autowired
    private MarketDataRepository marketDataRepository;

    @Autowired
    private CoinMarketCapApi coinMarketCapApi;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private Bugsnag bugsnag;
    @Autowired
    private ScanStrategyRepository scanStrategyRepository;
    @Autowired
    private ExchangeInfoRepository exchangeInfoRepository;

    private List<MarketData> previousData;
    private List<StrategyResult> previousResults;

    public Scanner() {
        previousData = new ArrayList<>();
        previousResults = new ArrayList<>();
    }

    public void scanCoinMarketCap() {

        List<ExchangeInfo> enabledExchanges = exchangeInfoRepository.findByScanEnabled(true);
        List<MarketData> currentData = coinMarketCapApi.getMarketData();
        List<StrategyResult> scanResults = new ArrayList<>();

        if (aggregate) {
            log.debug(String.format("%s", new ColorString("Storing aggregate data...", Colors.Green)));
            for (MarketData m : currentData) {
                marketDataRepository.save(m);
            }
        }

        if (previousData.size() == 0) {
            log.info("No previous data...");
            previousData = currentData;
            return;
        }

        for (MarketData c : currentData) {
            for (MarketData p : previousData) {
                if (c.getCoinId().equals(p.getCoinId()) && c.getLastUpdated() != p.getLastUpdated()) {

                    Set<String> targetExchanges = new HashSet<>();
                    boolean shouldScan = false;
                    for (ExchangeInfo exchange : enabledExchanges) {
                        if (exchange.hasCurrency(c.getSymbol())) {
                            targetExchanges.add(exchange.getCode());
                            shouldScan = true;
                        }
                    }

                    if (!shouldScan)
                        continue;

                    // Run strategies on the market data
                    for (ScanStrategy s : scanStrategyRepository.findByEnabled(true)) {
                        StrategyResult res = runStrategy(s, c, p);
                        if (res.isMatch()) {
                            res.setTargetExchanges(targetExchanges);
                            scanResults.add(res);
                        }
                    }

                    break;
                }
            }

            // TODO run confirmations on the previous results
            if (previousResults.size() > 0) {
                for (StrategyResult s : previousResults) {
                    MarketData d = s.getMarketData();
                    if (d.getSymbol().equals(c.getSymbol())) {
                        if (c.getPercentChangeHour() > d.getPercentChangeHour()) {
                            // what if it is a new hour?
                            log.debug("Confirm: " + d.getSymbol());
                        }
                    }
                }
            }
        }

        notificationService.notifyScanResults(scanResults);

        previousResults = scanResults;
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
        if (s.getPercentChangeHourIncreased() != null && s.getPercentChangeHourIncreased() != (c.getPercentChangeHour() > p.getPercentChangeHour()))
            return result;
        if (s.getMinPercentChangeDay() != null && c.getPercentChangeDay() < s.getMinPercentChangeDay())
            return result;
        if (s.getMaxPercentChangeDay() != null && c.getPercentChangeDay() > s.getMaxPercentChangeDay())
            return result;
        if (s.getPercentChangeDayIncreased() != null && s.getPercentChangeDayIncreased() != (c.getPercentChangeDay() > p.getPercentChangeDay()))
            return result;
        if (s.getMinPercentChangeWeek() != null && c.getPercentChangeWeek() < s.getMinPercentChangeWeek())
            return result;
        if (s.getMaxPercentChangeWeek() != null && c.getPercentChangeWeek() > s.getMaxPercentChangeWeek())
            return result;
        if (s.getPercentChangeWeekIncreased() != null && s.getPercentChangeWeekIncreased() != (c.getPercentChangeWeek() > p.getPercentChangeWeek()))
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


    public float getPercentChange(float newValue, float oldValue) {
        return ((newValue - oldValue) / oldValue) * 100;
    }
}
