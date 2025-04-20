package com.rw.apps.starter.common.validation.validators;

import com.rw.apps.starter.common.validation.annotations.MinimumAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Component;

@Component
public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {
    private static final int MINIMUM_AGE = 15;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || computeAge(value) >= MINIMUM_AGE;
    }

    private int computeAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
