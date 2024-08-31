package org.robe.fpa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Transaction {
    private Long transactionId;
    @NotNull
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private BigDecimal targetAmount;
    private String currencyCode;
    @NotNull
    private TransactionType type;
    private LocalDateTime transactionDate;
    private String description;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean scheduled;
    private LocalDateTime scheduledDate;
}
