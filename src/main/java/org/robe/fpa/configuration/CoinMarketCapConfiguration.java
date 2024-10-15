package org.robe.fpa.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="application.currency.cmc")
public class CoinMarketCapConfiguration {
    private String url;
    private String appId;
    private String slug;
}
