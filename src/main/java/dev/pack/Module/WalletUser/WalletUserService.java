package dev.pack.Module.WalletUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletUserService {

    private final WalletUserRepository walletUserRepository;
    private final WalletUserResponseMapper responseMapper;

    //TODO : CREATE METHOD ADDING BALANCE FOR WALLET USER WITH ID WALLET USER.

    @Transactional(rollbackOn = {Exception.class})
    public void addBalanceForWalletUser(BigInteger amount, WalletUser walletUser){
        BigInteger MAX_AMOUNT = BigInteger.valueOf(10000000);
        BigInteger currentBalance = walletUser.getUserBalance();

        if(amount.compareTo(MAX_AMOUNT) > 0) throw new IllegalArgumentException(
                String.format("Max amount of balance is %s!",MAX_AMOUNT)
        );
        BigInteger newBalance = currentBalance.add(amount);

        walletUser.setUserBalance(newBalance);

        walletUserRepository.save(walletUser);
    }

    public List<WalletUserResponse> getWalletUserData(){
        return walletUserRepository
                .findAll()
                .stream()
                .map(responseMapper)
                .collect(Collectors.toList());
    }

}