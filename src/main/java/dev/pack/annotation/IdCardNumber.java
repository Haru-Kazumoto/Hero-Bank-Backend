package dev.pack.annotation;

import dev.pack.validator.IdCardNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdCardNumberValidator.class)
@Documented
public @interface IdCardNumber {

    String message() default "Invalid ID card number";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};

}
