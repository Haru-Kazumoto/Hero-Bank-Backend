package dev.pack.Module.WalletUser.Response;

import dev.pack.Module.WalletUser.Model.WalletUser;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WalletUserResponseMapper implements Function<WalletUser, WalletUserResponse > {

    @Override
    public WalletUserResponse apply(WalletUser walletUser) {
        return new WalletUserResponse(
                walletUser.getId(),
                walletUser.getUserBalance(),
                walletUser.getPocketBalance()
        );
    }
}
