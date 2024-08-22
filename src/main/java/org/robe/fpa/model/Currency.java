package org.robe.fpa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {
    private String currencyCode;
    private String currencyName;
    private BigDecimal exchangeRate;
    private String baseCurrencyCode;
    private LocalDateTime createdAt;
}
