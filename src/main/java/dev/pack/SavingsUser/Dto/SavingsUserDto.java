package dev.pack.SavingsUser.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigInteger;

@Data
public class SavingsUserDto {

    @NotEmpty(message = "Title is required")
    private String title;

    @Max(value = 10000000, message = "Collected plans is only up to 10.000.000!")
    private BigInteger collectedPlans;
}
