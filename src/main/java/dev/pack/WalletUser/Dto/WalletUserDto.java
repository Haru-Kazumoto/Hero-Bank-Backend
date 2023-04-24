package dev.pack.WalletUser.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class WalletUserDto {

    @NotEmpty(message = "Country must filled!")
    private BigInteger userBalance;

    @NotEmpty(message = "Country must filled!")
    private BigInteger pocketBalance;

}
