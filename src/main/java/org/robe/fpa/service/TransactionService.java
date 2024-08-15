package org.robe.fpa.service;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Transaction;
import org.robe.fpa.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public long createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public boolean updateTransaction(Long transactionId, Transaction transactionDetails) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (transactionOptional.isPresent()) {
            var transaction = transactionOptional.get();
            transaction.setTransactionId(transactionDetails.getTransactionId());
            transaction.setAmount(transactionDetails.getAmount());
            transaction.setCurrencyCode(transactionDetails.getCurrencyCode());
            transaction.setType(transactionDetails.getType());
            transaction.setDescription(transactionDetails.getDescription());
            transaction.setStatus(transactionDetails.getStatus());
            transaction.setScheduled(transactionDetails.isScheduled());
            transaction.setScheduledDate(transactionDetails.getScheduledDate());
            transaction.setSourceAccountId(transactionDetails.getSourceAccountId());
            transaction.setTargetAccountId(transactionDetails.getTargetAccountId());
            
            transactionRepository.save(transaction);
            
            return true;
        }
        return false;
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
