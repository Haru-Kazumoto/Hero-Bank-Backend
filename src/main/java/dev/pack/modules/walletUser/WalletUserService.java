package dev.pack.modules.walletUser;

import java.util.List;

public interface WalletUserService {

    WalletUser topUpBalance(WalletUser walletUser);
    List<WalletUser> getAllWalletUser();

}
