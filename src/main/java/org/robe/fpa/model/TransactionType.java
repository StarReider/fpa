package org.robe.fpa.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {
    DEBIT("debit"),
    CREDIT("credit"),
    INTEREST("interest");
    
    private String name;
    
    TransactionType(String name) {
        this.name = name;
    }
    
    @JsonValue
    public String getName() {
        return name;
    }
    
    public static TransactionType valueOfName(String name) {
        for (TransactionType transactionType : values()) {
            if(transactionType.getName().equals(name)) {
                    return transactionType;
            }
        }
        
        return null;
    }
}
