package org.robe.fpa.model.cmc;

import java.util.Map;

import lombok.Data;

@Data
public class CmcResponse {
    private Status status;
    private Map<String, Currency> data;
}
