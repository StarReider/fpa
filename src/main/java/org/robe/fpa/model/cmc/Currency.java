package org.robe.fpa.model.cmc;

import java.util.Map;

import lombok.Data;

@Data
public class Currency {
    private int id;
    private String name;
    private String slug;
    private String symbol;
    private Map<String, Price> quote;
}
