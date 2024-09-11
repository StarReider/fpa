package org.robe.fpa.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.robe.fpa.validation.InterestValidation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@InterestValidation
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
    private InterestFrequency interestFrequency;
    private Long interestAccountId; // target account for interest payments
    private LocalDate interestStartDate;
    private LocalDate interestEndDate;
    private BigDecimal tax;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
