package org.robe.fpa.repository.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.robe.fpa.model.Currency;
import org.robe.fpa.repository.CurrencyRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final CurrencyMapper currencyMapper;
    
    private SimpleJdbcCall deleteFiatJdbcCall;
    
    @PostConstruct
    public void init() {
        deleteFiatJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("delete_fiat");
    }
    
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

    @Override
    public void save(List<Currency> currencies) {
        SqlParameterSource[] paramSources = currencies.stream()
                .map(this::prepareCurrencyForInsert)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(Queries.CREATE_CURRENCY, paramSources);
    }
    
    @Override
    public void deleteAll() {
        deleteFiatJdbcCall.execute();
    }
    
    private SqlParameterSource prepareCurrencyForInsert(Currency currency) {
        return new MapSqlParameterSource()
                .addValue("currency_code", currency.getCurrencyCode())
                .addValue("currency_name", currency.getCurrencyName())
                .addValue("exchange_rate", currency.getExchangeRate())
                .addValue("base_currency", currency.getBaseCurrencyCode())
                .addValue("type", currency.getType(), Types.OTHER);
    }
}
