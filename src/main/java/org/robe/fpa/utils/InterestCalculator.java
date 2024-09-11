package org.robe.fpa.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;

import org.robe.fpa.model.Account;

public class InterestCalculator {
    
    public static BigDecimal calculate(Account account) {
        switch (account.getInterestFrequency()) {
            case DAILY: {
                var daysInYear = ( Year.isLeap( LocalDate.now().getYear() ) ) ? new BigDecimal("366") : new BigDecimal("365") ;
                BigDecimal dailyRate = account.getInterestRate().divide(daysInYear, 10, RoundingMode.HALF_UP);
                return applyTax(dailyRate.multiply(account.getBalance()).scaleByPowerOfTen(-2), account.getTax());
            }
            case MONTHLY: {
                BigDecimal monthlyRate = account.getInterestRate().divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
                return applyTax(monthlyRate.multiply(account.getBalance()).scaleByPowerOfTen(-2), account.getTax());
            }
            case YEARLY: {
                return applyTax(account.getInterestRate().multiply(account.getBalance()).scaleByPowerOfTen(-2), account.getTax());
            }
            default:
                return null;
            }
    }
    
    private static BigDecimal applyTax(BigDecimal value, BigDecimal tax) {
        if(BigDecimal.ZERO == tax) {
            return value;
        }
        
        var taxValue = value.multiply(tax).scaleByPowerOfTen(-2);
        return value.subtract(taxValue);
    }
}
