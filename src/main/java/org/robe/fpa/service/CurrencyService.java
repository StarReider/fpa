package org.robe.fpa.service;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Currency;
import org.robe.fpa.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    
    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> getCurrencyByCode(String currencyCode) {
        return currencyRepository.findByCode(currencyCode);
    }

    public void createCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    public void deleteCurrency(String currencyCode) {
        currencyRepository.deleteById(currencyCode);
    }
}
