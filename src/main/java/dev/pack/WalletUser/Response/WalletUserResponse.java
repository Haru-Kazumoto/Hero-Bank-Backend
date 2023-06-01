package dev.pack.WalletUser.Response;

import java.math.BigInteger;
import java.util.UUID;

public record WalletUserResponse(
        UUID id,
        BigInteger userBalance,
        BigInteger pocketBalance
){}
