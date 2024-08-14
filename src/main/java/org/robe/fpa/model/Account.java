package org.robe.fpa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Account {
    private Long accountId;
    private Long userId;
    private String accountName;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private BigDecimal interestRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
