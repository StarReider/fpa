package org.robe.fpa.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.robe.fpa.model.Currency;
import org.robe.fpa.repository.CurrencyRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CurrencyMapper currencyMapper;
    
    @Override
    public List<Currency> findAll() {
        return namedParameterJdbcTemplate.query(Queries.FIND_ALL_CURRENCIES, currencyMapper);
    }

    @Override
    public Optional<Currency> findByCode(String currencyCode) {
        return Optional.of(namedParameterJdbcTemplate.queryForObject(Queries.FIND_CURRENCY_BY_CODE, Map.of("code", currencyCode), currencyMapper));
    }

    @Override
    public void save(Currency currency) {
        SqlParameterSource parameterSource = prepareCurrencyForInsert(currency);
        namedParameterJdbcTemplate.update(Queries.CREATE_CURRENCY, parameterSource);
    }

    @Override
    public void deleteById(String currencyCode) {
        namedParameterJdbcTemplate.update(Queries.DELETE_CURRENCY_BY_CODE, Map.of("code", currencyCode));
    }
    
    private SqlParameterSource prepareCurrencyForInsert(Currency currency) {
        return new MapSqlParameterSource()
                .addValue("currency_code", currency.getCurrencyCode())
                .addValue("currency_name", currency.getCurrencyName())
                .addValue("exchange_rate", currency.getExchangeRate())
                .addValue("base_currency", currency.getBaseCurrencyCode());
    }
}
