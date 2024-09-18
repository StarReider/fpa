package org.robe.fpa.service.impl;

import java.time.LocalDate;

import org.robe.fpa.model.Account;
import org.robe.fpa.model.InterestFrequency;
import org.robe.fpa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InterestService {
    
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountRepository accountRepository;
    
    public void calculateInterests() {
        log.info("create interests job is started");
        
        var accounts = accountRepository.findAllWithInterest();
        log.info("{} accounts with interests were found", accounts.size());
        
        for(var acc : accounts) {
            try {
                if(!isInterestShoudBePaid(acc)) {
                    continue;
                }
                
                var tr = transactionService.createInterestTransaction(acc, acc.getAccountId());
                transactionService.createTransaction(tr);
            } catch (Exception e) {
                log.error("Error while creating interest transaction for account " + acc.getAccountId(), e);
            }       
        }
    }
    
    private boolean isInterestShoudBePaid(Account acc) {
        return (InterestFrequency.DAILY == acc.getInterestFrequency() && 
                    (LocalDate.now().isAfter(acc.getInterestStartDate())) || LocalDate.now().isEqual(acc.getInterestStartDate())) ||
               (InterestFrequency.MONTHLY == acc.getInterestFrequency() && 
                    (acc.getInterestStartDate().plusDays(1).isEqual(LocalDate.now()) || 
                            LocalDate.now().getDayOfMonth() == acc.getInterestStartDate().plusDays(1).getDayOfMonth()) || 
               (InterestFrequency.YEARLY == acc.getInterestFrequency() && 
                       LocalDate.now().getDayOfYear() == acc.getInterestStartDate().plusYears(1).plusDays(1).getDayOfYear()));
    }
}
