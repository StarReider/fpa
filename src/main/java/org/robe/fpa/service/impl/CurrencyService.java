package org.robe.fpa.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.robe.fpa.configuration.CurrencyConfiguration;
import org.robe.fpa.model.Currency;
import org.robe.fpa.model.CurrencyType;
import org.robe.fpa.repository.CurrencyRepository;
import org.robe.fpa.service.CmcRatesService;
import org.robe.fpa.service.OpenExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyService {
    
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private OpenExchangeRatesService fiatrRatesService;
    @Autowired
    private CmcRatesService cryptoRatesService;
    @Autowired
    private CurrencyConfiguration configuration;

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> getCurrencyByCode(String currencyCode) {
        return currencyRepository.findByCode(currencyCode);
    }

    public void createCurrency(Currency currency) {
        currencyRepository.save(currency);
    }
    
    public void deleteAllFiat() {
        currencyRepository.deleteAllFiat();
    }
    
    public void deleteAllCrypto() {
        currencyRepository.deleteAllCrypto();
    }
    
    public boolean updateCurrency(String currencyCode, Currency currencyDetails) {
        Optional<Currency> currencyOptional = currencyRepository.findByCode(currencyCode);
        if (currencyOptional.isPresent()) {
            var currency = currencyOptional.get();
            currency.setBaseCurrencyCode(currencyDetails.getBaseCurrencyCode());
            currency.setCurrencyCode(currencyDetails.getBaseCurrencyCode());
            currency.setCurrencyName(currencyDetails.getCurrencyName());
            currency.setExchangeRate(currencyDetails.getExchangeRate());
            currency.setType(currencyDetails.getType());

            currencyRepository.save(currency);
            
            return true;
        }
        return false;
    }

    public void deleteCurrency(String currencyCode) {
        currencyRepository.deleteById(currencyCode);
    }
    
    @Transactional
    public void syncFiatCurrencies() {
        var response = fiatrRatesService.retriveRates();
        List<Currency> currencies = transformFiat(configuration.getBaseCurrency(), response.getRates());
        
        currencyRepository.deleteAllFiat();
        currencyRepository.save(currencies);
    }
    
    @Transactional
    public void syncCryptoCurrencies() {
        var response = cryptoRatesService.retriveRates();
        List<Currency> currencies = transformCrypto(configuration.getBaseCurrency(), response.getData());
        
        currencyRepository.deleteAllCrypto();
        currencyRepository.save(currencies);
    }

    private List<Currency> transformFiat(String base, Map<String, String> rates) {
        return rates.entrySet().stream()
                .map(entry -> Currency.builder()
                        .baseCurrencyCode(base)
                        .currencyCode(entry.getKey())
                        .currencyName(entry.getKey())
                        .exchangeRate(new BigDecimal(entry.getValue()))
                        .type(CurrencyType.FIAT)
                        .build()).toList();
    }
    
    private List<Currency> transformCrypto(String base, Map<String, org.robe.fpa.model.cmc.Currency> data) {
        return data.entrySet().stream()
                .map(entry -> {
                    var currencyToPriceMap = entry.getValue().getQuote();
                    if(currencyToPriceMap.get(base) == null) {
                        return null;
                    }
                    
                    var price = currencyToPriceMap.get(base).getPrice();
                    return Currency.builder()
                        .baseCurrencyCode(base)
                        .currencyCode(entry.getValue().getSymbol())
                        .currencyName(entry.getValue().getSymbol())
                        .exchangeRate(new BigDecimal(price))
                        .type(CurrencyType.CRYPTO)
                        .build();
                }).toList();
    }
}
