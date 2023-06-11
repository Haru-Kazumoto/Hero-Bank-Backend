package dev.pack.Module.User.Response;

import dev.pack.Module.UserInfo.Model.UserInfo;

import java.util.UUID;

public record OutputResponse(
        UUID id,
        String pin,
        UserInfo info
) {
}
