package io.retrofrog.frogbot.services;

import com.bugsnag.Bugsnag;
import io.retrofrog.frogbot.integrations.coinigy.CoinigyRestApi;
import io.retrofrog.frogbot.integrations.coinigy.exceptions.CoinigyException;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyAccount;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyExchange;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyMarket;
import io.retrofrog.frogbot.integrations.coinigy.models.rest.CoinigyResponse;
import io.retrofrog.frogbot.models.ExchangeInfo;
import io.retrofrog.frogbot.repositories.ExchangeInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class CoinigyService {
    private static final Logger log = LoggerFactory.getLogger(CoinigyService.class);

    @Autowired
    private Bugsnag bugsnag;

    @Autowired
    private CoinigyRestApi coinigyRestApi;


    @Autowired
    private ExchangeInfoRepository exchangeInfoRepository;


    public void updateExchangeData() {
        if (!coinigyRestApi.isEnabled()) {
            log.info("Coinigy is disabled... skipping.");
            return;
        }

        try {
            CoinigyResponse<CoinigyExchange> er = coinigyRestApi.getExchanges();
            for (CoinigyExchange e : er.getData()) {
                ExchangeInfo ei = new ExchangeInfo(e);
                try {
                    exchangeInfoRepository.save(ei);
                } catch (DuplicateKeyException ex) {
                }
            }

            CoinigyResponse<CoinigyAccount> ar = coinigyRestApi.getAccounts();
            for (CoinigyAccount a : ar.getData()) {
                ExchangeInfo ei = exchangeInfoRepository.findOneByExchangeId(a.getExchangeId());
                if (ei == null) {
                    log.warn("Missing exchange info for " + a.getExchangeName());
                    continue;
                }

                if (ei.getAuthId() != null)
                    continue;

                ei.setAuthId(a.getAuthId());
                ei.setScanEnabled(a.isTradeEnabled());
                exchangeInfoRepository.save(ei);
            }

            CoinigyResponse<CoinigyMarket> mr = coinigyRestApi.getMarkets();
            for (CoinigyMarket m : mr.getData()) {
                ExchangeInfo ei = exchangeInfoRepository.findOneByExchangeId(m.getExchangeId());
                if (ei == null) {
                    log.warn("Missing exchange info for " + m.getExchangeName());
                    continue;
                }

                ei.addCurrency(m.getAsset());
                exchangeInfoRepository.save(ei);
            }
        } catch (CoinigyException ex) {
            bugsnag.notify(ex);
        }
    }
}
