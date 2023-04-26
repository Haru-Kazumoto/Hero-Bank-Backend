package dev.pack.WalletUser.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class WalletUserDto {

    @Column(columnDefinition = "BigInteger default 0")
    @Max(value = 10000000, message = "Maximal value is 10000000")
    private BigInteger userBalance;

    @Column(columnDefinition = "BigInteger default 0")
    @Max(value = 20000000, message = "Maximal value is 20000000")
    private BigInteger pocketBalance;

}
