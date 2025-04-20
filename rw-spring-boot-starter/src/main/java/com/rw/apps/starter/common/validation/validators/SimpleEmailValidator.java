package com.rw.apps.starter.common.validation.validators;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;
import org.springframework.stereotype.Component;

@Component
public class SimpleEmailValidator extends AbstractEmailValidator<Email> {
}
