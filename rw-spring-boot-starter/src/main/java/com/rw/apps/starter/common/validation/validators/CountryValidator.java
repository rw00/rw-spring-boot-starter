package com.rw.apps.starter.common.validation.validators;

import com.rw.apps.starter.common.model.Country;
import com.rw.apps.starter.common.validation.annotations.ValidCountry;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EnumUtils.getEnum(Country.class, value) != null;
    }
}
