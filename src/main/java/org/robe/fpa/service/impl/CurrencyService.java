package org.robe.fpa.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.robe.fpa.model.Account;
import org.robe.fpa.model.Currency;
import org.robe.fpa.repository.CurrencyRepository;
import org.robe.fpa.service.OpenExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyService {
    
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private OpenExchangeRatesService ratesService;

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> getCurrencyByCode(String currencyCode) {
        return currencyRepository.findByCode(currencyCode);
    }

    public void createCurrency(Currency currency) {
        currencyRepository.save(currency);
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
    public void syncCurrencies() {
        var response = ratesService.retriveRates();
        List<Currency> currencies = transform(response.getBase(), response.getRates());
        currencyRepository.deleteAll();
        currencyRepository.save(currencies);
    }

    private List<Currency> transform(String base, Map<String, String> rates) {
        return rates.entrySet().stream()
                .map(entry -> Currency.builder()
                        .baseCurrencyCode(base)
                        .currencyCode(entry.getKey())
                        .currencyName(entry.getKey())
                        .exchangeRate(new BigDecimal(entry.getValue()))
                        .build()).toList();
    }
}
