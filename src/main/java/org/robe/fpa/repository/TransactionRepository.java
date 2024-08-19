package org.robe.fpa.repository;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Transaction;

public interface TransactionRepository {
    List<Transaction> findByAccountId(Long accountId);
    Optional<Transaction> findById(Long accountId);
    Long save(Transaction transaction);
    List<Transaction> findAll();
    void deleteById(Long transactionId);
    List<Transaction> findScheduled();
}
