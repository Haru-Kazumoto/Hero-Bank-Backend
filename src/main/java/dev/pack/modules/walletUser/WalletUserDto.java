package dev.pack.modules.walletUser;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Data
public class WalletUserDto {

    @NotEmpty(message = "The amount of balance is empty!")
    @Max(value = 50_000_000L,message = "Hero bank maximum balance is only up to Rp50.000.000")
    @Min(value = 100_000L, message = "Hero bank minimum balance is Rp100.000")
    private Long amount;

    @NotEmpty(message = "User id is required for relational!")
    @NotNull(message = "User id is required for relational!")
    private UUID userId;
}
