package dev.pack.payload.response;

import dev.pack.modules.userInfo.UserInfo;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String pin,
        UserInfo info
) {
}
