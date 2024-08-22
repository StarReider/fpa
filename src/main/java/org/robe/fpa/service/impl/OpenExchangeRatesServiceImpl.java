package org.robe.fpa.service.impl;

import java.util.Map;

import org.robe.fpa.OpenExchangeRatesConfiguration;
import org.robe.fpa.model.OpenExchangeRateResponse;
import org.robe.fpa.service.OpenExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OpenExchangeRatesServiceImpl implements OpenExchangeRatesService {
    
    private RestClient restClient;
    @Autowired
    private OpenExchangeRatesConfiguration exchangeRatesConfiguration;
    
    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
                .baseUrl(exchangeRatesConfiguration.getUrl())
                .defaultUriVariables(Map.of("app_id", exchangeRatesConfiguration.getAppId()))
                .build();
    }
    
    @Override
    public OpenExchangeRateResponse retriveRates() {
        try {
            return restClient.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(OpenExchangeRateResponse.class);
        } catch(HttpClientErrorException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
        }
    }
}
