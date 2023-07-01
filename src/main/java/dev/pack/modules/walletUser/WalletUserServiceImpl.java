package dev.pack.modules.walletUser;

import dev.pack.exception.IdNotFoundException;
import dev.pack.utils.Generate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletUserServiceImpl implements WalletUserService{

    private final WalletUserRepository walletUserRepository;
    private final Generate generate;

    @Value("${wallet-user.balance.request.max}")
    int MAX_REQUEST_BALANCE;

    @Value("${wallet-user.balance.request.min}")
    int MIN_REQUEST_BALANCE;

    @Override
    @Transactional(rollbackOn = {
            NullPointerException.class,
            IdNotFoundException.class,
            Exception.class
    })
    public WalletUser topUpBalance(WalletUser walletUser) { //Adding balance
        return null;
    }

    @Override
    public List<WalletUser> getAllWalletUser() {
        return null;
    }
}
