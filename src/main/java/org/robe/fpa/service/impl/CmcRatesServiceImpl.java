package org.robe.fpa.service.impl;

import java.util.Map;

import org.robe.fpa.configuration.CoinMarketCapConfiguration;
import org.robe.fpa.configuration.CurrencyConfiguration;
import org.robe.fpa.model.cmc.CmcResponse;
import org.robe.fpa.service.CmcRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CmcRatesServiceImpl implements CmcRatesService {
    
    private RestClient restClient;
    @Autowired
    private CoinMarketCapConfiguration coinMarketCapConfiguration;
    @Autowired
    private CurrencyConfiguration currencyConfiguration;
    
    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
                .baseUrl(coinMarketCapConfiguration.getUrl())
                .defaultUriVariables(Map.of(
                        "slug", coinMarketCapConfiguration.getSlug(), 
                        "base-currency", currencyConfiguration.getBaseCurrency()))
                .build();
    }
    
    @Override
    public CmcResponse retriveRates() {
        try {
            return restClient.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .header("X-CMC_PRO_API_KEY", coinMarketCapConfiguration.getAppId())
                    .retrieve()
                    .body(CmcResponse.class);
        } catch(Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
        }
    }
}
