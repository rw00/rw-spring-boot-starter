package com.rw.apps.starter.common.validation.validators;

import com.rw.apps.starter.common.validation.annotations.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final int MIN_PHONE_NUMBER_LENGTH = 12;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || (value.length() >= MIN_PHONE_NUMBER_LENGTH
                                 && value.startsWith("+")
                                 && allDigits(value));
    }

    private boolean allDigits(String value) {
        int len = value.length();
        for (int i = 1; i < len; i++) {
            char ch = value.charAt(i);
            if (!isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
}
