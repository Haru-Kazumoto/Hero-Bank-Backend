package dev.pack.Validator;

import dev.pack.Annotation.IdCardNumber;
import dev.pack.Module.UserInfo.UserInfo;
import dev.pack.Module.UserInfo.UserInfoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IdCardNumberValidator implements ConstraintValidator<IdCardNumber, String> {

    private final UserInfoRepository userInfoRepository;

    private static final String PREFIX_ID = "3725";
    private static final int CHAR_LENGTH = 16;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }

        return value.length() == CHAR_LENGTH
                && value.startsWith(PREFIX_ID)
                && value.matches("\\d+")
                && isUnique(value);
    }

    public boolean isUnique(String value){
        UserInfo isIdCardUser = userInfoRepository.findUserInfoByIdCardNumber(value);

        return isIdCardUser == null; //If true it'll be error
    }
}
