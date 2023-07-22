package dev.pack.modules.walletUser;

import dev.pack.utils.Generate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletUserServiceImpl implements WalletUserService{

    private final Generate generate;
    private final WalletUserRepository repository;

    @Override
    public WalletUser createWalletUserBody(WalletUser body) {
        WalletUser bodyWallet = WalletUser.builder()
                .userBalance(0L)
                .pocketBalance(0L)
                .walletId(generate.randomIdNumber(12))
                .build();

        return repository.save(bodyWallet);
    }
}
