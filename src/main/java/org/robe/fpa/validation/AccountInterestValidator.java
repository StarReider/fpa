package org.robe.fpa.validation;

import java.math.BigDecimal;

import org.robe.fpa.model.Account;
import org.robe.fpa.model.AccountType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountInterestValidator implements ConstraintValidator<InterestValidation, Account> {

    @Override
    public boolean isValid(Account account, ConstraintValidatorContext context) {
        var rate = account.getInterestRate();
        if(rate != null && account.getType() != AccountType.SAVINGS) {
            return false;
        }
        
        if(rate != null && rate.compareTo(BigDecimal.ZERO) == 1) {
            if(account.getInterestFrequency() == null || account.getInterestEndDate() == null || account.getInterestAccountId() == null) {
                return false;
            }
            
            if(account.getInterestEndDate().isBefore(account.getInterestStartDate()) ) {
                return false;
            }
        }
        
        return true;
    }
}
