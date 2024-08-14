package org.robe.fpa.repository;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Currency;

public interface CurrencyRepository {
    List<Currency> findAll();
    Optional<Currency> findByCode(String currencyCode);
    void save(Currency currency);
    void deleteById(String currencyCode);
}
