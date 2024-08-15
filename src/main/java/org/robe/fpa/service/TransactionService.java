package org.robe.fpa.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Transaction;
import org.robe.fpa.model.TransactionStatus;
import org.robe.fpa.repository.AccountRepository;
import org.robe.fpa.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public long createTransaction(Transaction transaction) {
        var sourceAccount = accountRepository.findById(transaction.getSourceAccountId());
        
        if(sourceAccount.isEmpty()) {
            throw new IllegalArgumentException("There are no account with id " + transaction.getSourceAccountId());
        }
        
        if (sourceAccount.get().getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the source account.");
        }
        
        long id = transactionRepository.save(transaction);
        if(transaction.isScheduled() && transaction.getScheduledDate() != null && transaction.getScheduledDate().isAfter(LocalDateTime.now())) {
            return id;
        }
        
        var newSourceBalance = sourceAccount.get().getBalance().subtract(transaction.getAmount());
        sourceAccount.get().setBalance(newSourceBalance);
        accountRepository.save(sourceAccount.get());
        
        if(transaction.getTargetAccountId() != null) {
            var destinationAccount = accountRepository.findById(transaction.getTargetAccountId()).orElse(null);
            if (destinationAccount != null) {
                BigDecimal newDestinationBalance = destinationAccount.getBalance().add(transaction.getAmount());
                destinationAccount.setBalance(newDestinationBalance);
                accountRepository.save(destinationAccount);
            }
        }
        
        transaction.setStatus(TransactionStatus.COMPLETED);
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
