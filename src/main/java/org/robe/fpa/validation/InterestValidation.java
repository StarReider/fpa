package org.robe.fpa.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RUNTIME)
@Target({ TYPE })
@Constraint(validatedBy = AccountInterestValidator.class)
public @interface InterestValidation {
    String message() default "Interest configuration is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
