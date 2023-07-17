package dev.pack.validator;

import dev.pack.annotation.OutletPayments;
import dev.pack.enums.OutletPaymentsEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OutletPaymentsValidator implements ConstraintValidator<OutletPayments, OutletPaymentsEnum> {
    @Override
    public boolean isValid(OutletPaymentsEnum outletPaymentsEnum, ConstraintValidatorContext constraintValidatorContext) {
        List<OutletPaymentsEnum> outletsPayment = List.of(OutletPaymentsEnum.values());
        return outletsPayment.contains(outletPaymentsEnum);
    }
}
