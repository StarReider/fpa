package org.robe.fpa.repository.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.robe.fpa.model.Account;
import org.robe.fpa.model.AccountType;
import org.robe.fpa.model.InterestFrequency;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements RowMapper<Account> {
    
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        var account = new Account();
        
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setAccountName(rs.getString("account_name"));
        account.setType(AccountType.valueOf(rs.getString("type")));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setCurrency(rs.getString("currency"));
        account.setInterestRate(rs.getBigDecimal("interest_rate"));
        
        if(rs.getLong("interest_account_id") != 0) { 
            account.setInterestAccountId(rs.getLong("interest_account_id"));
        }
        
        account.setInterestFrequency(InterestFrequency.valueOf(rs.getString("interest_frequency")));
        account.setInterestStartDate(
                Optional.ofNullable(rs.getDate("interest_start_date"))
                    .map(Date::toLocalDate)
                    .orElse(null));
        account.setInterestEndDate(
                Optional.ofNullable(rs.getDate("interest_end_date"))
                    .map(Date::toLocalDate)
                    .orElse(null));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        account.setUpdatedAt(
            Optional.ofNullable(rs.getTimestamp("updated_at"))
                .map(Timestamp::toLocalDateTime)
                .orElse(null));
        
        return account;
    }
}
