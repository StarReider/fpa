package org.robe.fpa.model.oe;

import java.util.Map;

import lombok.Data;

@Data
public class OeResponse {
    private long timestamp;
    private String base;
    private Map<String, String> rates;
}
