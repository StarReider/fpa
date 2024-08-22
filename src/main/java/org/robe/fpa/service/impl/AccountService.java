package org.robe.fpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.robe.fpa.model.Account;
import org.robe.fpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public Long createAccount(Account account) {
        return accountRepository.save(account);
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
