package dev.pack.Module.WalletUser.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class WalletUserDto {

    @NotEmpty(message = "The amount of balance is empty!")
    private BigInteger userBalance;

}
