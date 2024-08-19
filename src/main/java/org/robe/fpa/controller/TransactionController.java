package org.robe.fpa.controller;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Transaction;
import org.robe.fpa.service.ScheduledTransactionService;
import org.robe.fpa.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ScheduledTransactionService scheduledTransactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("id") Long transactionId) {
        Optional<Transaction> transactionOptional = transactionService.getTransactionById(transactionId);
        return transactionOptional.map(ResponseEntity::ok)
                                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable("accountId") Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createTransaction(@RequestBody Transaction transaction) {
        long id = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
    
    @PostMapping("/scheduled")
    public ResponseEntity<Void> runScheduled() {
        scheduledTransactionService.runScheduledTransactions();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") Long transactionId,
                                                         @RequestBody Transaction transactionDetails) {
        boolean updatedTransaction = transactionService.updateTransaction(transactionId, transactionDetails);
        return updatedTransaction ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
