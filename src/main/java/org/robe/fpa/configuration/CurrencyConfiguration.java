package org.robe.fpa.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="application.currency")
public class CurrencyConfiguration {
    private String baseCurrency;
}
