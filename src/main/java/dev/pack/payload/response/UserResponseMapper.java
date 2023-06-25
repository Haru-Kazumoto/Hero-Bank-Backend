package dev.pack.payload.response;

import dev.pack.modules.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserResponseMapper implements Function<UserEntity, UserResponse> {

    @Override
    public UserResponse apply(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getPin(),
                user.getUserInfo()
        );
    }
}
