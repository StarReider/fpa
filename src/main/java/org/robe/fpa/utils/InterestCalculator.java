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
                return dailyRate.multiply(account.getBalance()).scaleByPowerOfTen(-2);
            }
            case MONTHLY: {
                BigDecimal monthlyRate = account.getInterestRate().divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
                return monthlyRate.multiply(account.getBalance()).scaleByPowerOfTen(-2);
            }
            case YEARLY: {
                return account.getInterestRate().multiply(account.getBalance()).scaleByPowerOfTen(-2);
            }
            default:
                return null;
            }
    }
}
