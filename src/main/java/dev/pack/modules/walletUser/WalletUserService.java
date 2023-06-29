package dev.pack.modules.walletUser;

import java.util.List;

public interface WalletUserService {

    WalletUser addBalance(WalletUser walletUser);
    List<WalletUser> getAllWalletUser();

}
