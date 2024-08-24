package org.robe.fpa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transaction {
    private Long transactionId;
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private BigDecimal targetAmount;
    private String currencyCode;
    private TransactionType type;
    private LocalDateTime transactionDate;
    private String description;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean scheduled;
    private LocalDateTime scheduledDate;
}
