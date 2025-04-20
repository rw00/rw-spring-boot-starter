package com.rw.apps.starter.common.validation.annotations;

import com.rw.apps.starter.common.validation.validators.CountryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryValidator.class)
@Documented
public @interface ValidCountry {
    String message() default "invalid country";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
