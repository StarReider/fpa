package org.robe.fpa.controller;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Account;
import org.robe.fpa.service.impl.AccountService;
import org.robe.fpa.service.impl.InterestService;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private InterestService interestService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long accountId) {
        Optional<Account> accountOptional = accountService.getAccountById(accountId);
        return accountOptional.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Long> createAccount(@Valid @RequestBody Account account) {
        long id = accountService.createAccount(account);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
    
    @PostMapping("bulk")
    public ResponseEntity<Void> importAccounts(@Valid @RequestBody List<Account> accounts) {
        accountService.createAccounts(accounts);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Long accountId,
                                                 @RequestBody Account accountDetails) {
        boolean updated = accountService.updateAccount(accountId, accountDetails);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/interests")
    public ResponseEntity<Void> calculateInterests() {
        interestService.calculateInterests();
        return ResponseEntity.noContent().build();
    }
}
