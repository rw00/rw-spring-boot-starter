package com.rw.apps.starter.common.validation.annotations;

import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPassword {
    String message() default "password should be at least 10 characters long and contain at least 1 digit and 1 letter, without spaces";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
