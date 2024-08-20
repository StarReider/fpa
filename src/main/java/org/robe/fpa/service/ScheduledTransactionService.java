package org.robe.fpa.service;

import org.robe.fpa.model.TransactionStatus;
import org.robe.fpa.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduledTransactionService {
    
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;

    public void runScheduledTransactions() {
        log.info("scheduled transactions job is started");
        
        var transactions = transactionRepository.findScheduled();
        log.info("{} scheduled transactions were found", transactions.size());
        
        for(var trs : transactions) {
            try {
                transactionService.createTransaction(trs); 
            } catch (Exception e) {
                trs.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(trs);
                
                log.error("Error while processing transaction " + trs.getTransactionId(), e);
            } 
        }
    }
}
