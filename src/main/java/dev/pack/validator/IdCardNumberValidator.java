package dev.pack.validator;

import dev.pack.annotation.IdCardNumber;
import dev.pack.modules.userInfo.UserInfo;
import dev.pack.modules.userInfo.UserInfoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IdCardNumberValidator implements ConstraintValidator<IdCardNumber, String> {

    private final UserInfoRepository userInfoRepository;

    private static final String PREFIX_ID = "3275";
    private static final int CHAR_LENGTH = 16;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true; // Let @NotNull handle it
        if (value.length() != CHAR_LENGTH || !value.matches("\\d+")) return false;
        if (!value.startsWith(PREFIX_ID)) return false;

        return isUnique(value);
    }

    private boolean isUnique(String value) {
        UserInfo isIdCardUser = userInfoRepository.findUserInfoByIdCardNumber(value);

        return isIdCardUser == null;
    }
}
