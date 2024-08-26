package org.robe.fpa.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.robe.fpa.model.Currency;
import org.robe.fpa.model.CurrencyType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper implements RowMapper<Currency> {
    
    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        var currency = new Currency();
        
        currency.setBaseCurrencyCode(rs.getString("base_currency"));
        currency.setCurrencyCode(rs.getString("currency_code"));
        currency.setCurrencyName(rs.getString("currency_name"));
        currency.setType(CurrencyType.valueOf(rs.getString("type")));
        currency.setExchangeRate(rs.getBigDecimal("exchange_rate"));
        currency.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        
        return currency;
    }
}
