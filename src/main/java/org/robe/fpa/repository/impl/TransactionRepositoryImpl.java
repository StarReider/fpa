package org.robe.fpa.repository.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.robe.fpa.model.Transaction;
import org.robe.fpa.repository.TransactionRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {
    
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final TransactionMapper transactionMapper;
    
    @Override
    public List<Transaction> findScheduled() {
        return namedParameterJdbcTemplate.query(Queries.FIND_SCHEDULED_TRANSACTIONS, transactionMapper);
    }
    
    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        return namedParameterJdbcTemplate.query(Queries.FIND_TRANSACTION_BY_ACCOUNT_ID, Map.of("id", accountId), transactionMapper);
    }

    @Override
    public Optional<Transaction> findById(Long transactionId) {
        return Optional.of(namedParameterJdbcTemplate.queryForObject(Queries.FIND_TRANSACTION_BY_ID, Map.of("id", transactionId), transactionMapper));
    }

    @Override
    public Long save(Transaction transaction) {
        if(transaction.getTransactionId() == null) {
            SqlParameterSource parameterSource = prepareTransactionForInsert(transaction);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            namedParameterJdbcTemplate.update(Queries.CREATE_TRANSACTION, parameterSource, keyHolder, new String[] { "transaction_id" });
            return Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).orElse(null);
        } else {
            SqlParameterSource parameterSource = prepareTransactionForUpdate(transaction);
            namedParameterJdbcTemplate.update(Queries.UPDATE_TRANSACTION, parameterSource);
            return transaction.getTransactionId();
        }
    }

    @Override
    public List<Transaction> findAll() {
        return namedParameterJdbcTemplate.query(Queries.FIND_ALL_TRANSACTIONS, transactionMapper);
    }

    @Override
    public void deleteById(Long transactionId) {
        namedParameterJdbcTemplate.update(Queries.DELETE_TRANSACTION_BY_ID, Map.of("id", transactionId));
    }

    private SqlParameterSource prepareTransactionForInsert(Transaction transaction) {
        var parameterSource = new MapSqlParameterSource()
                .addValue("source_account_id", transaction.getSourceAccountId())
                .addValue("amount", transaction.getAmount())
                .addValue("target_amount", transaction.getTargetAmount())
                .addValue("type", transaction.getType().getName(), Types.OTHER)
                .addValue("description", transaction.getDescription())
                .addValue("is_scheduled", transaction.isScheduled())
                .addValue("scheduled_date", transaction.getScheduledDate())
                .addValue("status", transaction.getStatus(), Types.OTHER);
        
        if(transaction.getTargetAccountId() == null || transaction.getTargetAccountId() == 0) {
            parameterSource.addValue("target_account_id", null);
        } else {
            parameterSource.addValue("target_account_id", transaction.getTargetAccountId());
        }
        
        return parameterSource;
    }

    private SqlParameterSource prepareTransactionForUpdate(Transaction transaction) {
        return ((MapSqlParameterSource)prepareTransactionForInsert(transaction)).addValue("id", transaction.getTransactionId());
    }
}
