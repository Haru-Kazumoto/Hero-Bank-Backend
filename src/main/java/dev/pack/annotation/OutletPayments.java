package dev.pack.annotation;

import dev.pack.validator.OutletPaymentsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OutletPaymentsValidator.class)
public @interface OutletPayments {

    String message() default "Outlet payment is not supported :(";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
