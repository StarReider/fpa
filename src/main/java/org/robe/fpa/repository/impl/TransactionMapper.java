package org.robe.fpa.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import org.robe.fpa.model.Transaction;
import org.robe.fpa.model.TransactionStatus;
import org.robe.fpa.model.TransactionType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements RowMapper<Transaction> {
    
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        var transaction = new Transaction();
        
        transaction.setTransactionId(rs.getLong("transaction_id"));
        transaction.setStatus(TransactionStatus.valueOf(rs.getString("status")));
        transaction.setSourceAccountId(rs.getLong("source_account_id"));
        transaction.setTargetAccountId(rs.getLong("target_account_id") == 0 ? null : rs.getLong("target_account_id"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setTargetAmount(rs.getBigDecimal("target_amount"));
        transaction.setType(TransactionType.valueOfName(rs.getString("type")));
        transaction.setScheduled(rs.getBoolean("is_scheduled"));
        transaction.setDescription(rs.getString("description"));
        transaction.setScheduledDate(
                Optional.ofNullable(rs.getTimestamp("scheduled_date"))
                    .map(Timestamp::toLocalDateTime)
                    .orElse(null));
        
        transaction.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        transaction.setUpdatedAt(
            Optional.ofNullable(rs.getTimestamp("updated_at"))
                .map(Timestamp::toLocalDateTime)
                .orElse(null));
        
        return transaction;
    }
}
