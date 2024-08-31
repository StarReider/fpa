package org.robe.fpa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class Account {
    private Long accountId;
    @NotNull
    private Long userId;
    private String accountName;
    @NotNull
    private AccountType type;
    @PositiveOrZero
    private BigDecimal balance;
    @NotNull
    private String currency;
    private BigDecimal interestRate;
    private Long interestAccountId; // target account for interest payments
    private LocalDateTime interestDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
