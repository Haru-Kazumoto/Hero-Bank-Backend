package dev.pack.User.Response;

import dev.pack.UserInfo.Model.UserInfo;
import dev.pack.WalletUser.Model.WalletUser;

import java.util.List;
import java.util.UUID;

public record OutputResponse(
        UUID id,
        String pin,
        UserInfo info
) {
}
