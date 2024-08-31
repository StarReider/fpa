package org.robe.fpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Account;
import org.robe.fpa.model.AccountType;
import org.robe.fpa.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public Long createAccount(Account account) {
        long id = accountRepository.save(account);
        if(AccountType.SAVINGS == account.getType() && account.getInterestRate() != null && account.getInterestRate().signum() == 1) {
            var tr = transactionService.createInterestTransaction(account, id);
            transactionService.createTransaction(tr);
        }
        return id;
    }

    public boolean updateAccount(Long accountId, Account accountDetails) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            var account = accountOptional.get();
            account.setAccountName(accountDetails.getAccountName());
            account.setType(accountDetails.getType());
            account.setBalance(accountDetails.getBalance());
            account.setCurrency(accountDetails.getCurrency());
            account.setInterestRate(accountDetails.getInterestRate());

            accountRepository.save(account);
            
            return true;
        }
        return false;
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}
