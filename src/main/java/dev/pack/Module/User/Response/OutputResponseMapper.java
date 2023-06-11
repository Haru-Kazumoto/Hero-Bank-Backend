package dev.pack.Module.User.Response;

import dev.pack.Module.User.Model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OutputResponseMapper implements Function<UserEntity, OutputResponse> {

    @Override
    public OutputResponse apply(UserEntity user) {
        return new OutputResponse(
                user.getId(),
                user.getPin(),
                user.getUserInfo()
        );
    }
}
