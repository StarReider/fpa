package org.robe.fpa.model;

import java.util.Map;

import lombok.Data;

@Data
public class OpenExchangeRateResponse {
    private long timestamp;
    private String base;
    private Map<String, String> rates;
}
