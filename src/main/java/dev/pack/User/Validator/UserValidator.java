package dev.pack.User.Validator;

import dev.pack.User.Model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    public void validate(UserEntity user){
        validatePinUser(user.getPin());
    }

    public void validatePinUser(String pin) {
        int pinLength = pin.length();
        if (pinLength < 4 || pinLength > 8) {
            throw new IllegalArgumentException("Pin must have minimal 4 number and maximum 8 number");
        }
    }
}
