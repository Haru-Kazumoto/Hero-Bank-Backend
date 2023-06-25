package dev.pack.modules.walletUser;

import java.math.BigInteger;
import java.util.UUID;

public record WalletUserResponse(
        UUID id,
        BigInteger userBalance,
        BigInteger pocketBalance
){}
