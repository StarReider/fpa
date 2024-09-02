package org.robe.fpa.repository.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.robe.fpa.model.Account;
import org.robe.fpa.repository.AccountRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final AccountMapper accountMapper;
    
    @Override
    public List<Account> findAll() {
        return namedParameterJdbcTemplate.query(Queries.FIND_ALL_ACCOUNTS, accountMapper);
    }

    @Override
    public Long save(Account account) {
        if(account.getAccountId() == null) {
            SqlParameterSource parameterSource = prepareAccountForInsert(account);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            namedParameterJdbcTemplate.update(Queries.CREATE_ACCOUNT, parameterSource, keyHolder, new String[] { "account_id" });
            return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).orElse(null);
        } else {
            SqlParameterSource parameterSource = prepareAccountForUpdate(account);
            namedParameterJdbcTemplate.update(Queries.UPDATE_ACCOUNT, parameterSource);
            return account.getUserId();
        }
    }

    @Override
    public Optional<Account> findById(Long accountId) {
        return Optional.of(namedParameterJdbcTemplate.queryForObject(Queries.FIND_ACCOUNT_BY_ID, Map.of("id", accountId), accountMapper));
    }

    @Override
    public void deleteById(Long accountId) {
        namedParameterJdbcTemplate.update(Queries.DELETE_ACCOUNT_BY_ID, Map.of("id", accountId));
    }

    @Override
    public List<Account> findAllWithInterest() { 
        return namedParameterJdbcTemplate.query(Queries.FIND_ALL_ACTIVE_ACCOUNTS_WITH_INTEREST, accountMapper);
    }

    private SqlParameterSource prepareAccountForInsert(Account account) {
        return new MapSqlParameterSource()
                .addValue("user_id", account.getUserId())
                .addValue("account_name", account.getAccountName())
                .addValue("type", account.getType(), Types.OTHER)
                .addValue("balance", account.getBalance())
                .addValue("currency", account.getCurrency())
                .addValue("interest_rate", account.getInterestRate())
                .addValue("interest_account_id", account.getInterestAccountId())
                .addValue("interest_end_date", account.getInterestEndDate())
                .addValue("interest_start_date", account.getInterestStartDate())
                .addValue("interest_account_id", account.getInterestAccountId());
    }

    private SqlParameterSource prepareAccountForUpdate(Account account) {
        return ((MapSqlParameterSource)prepareAccountForInsert(account)).addValue("id", account.getAccountId());
    }
}
