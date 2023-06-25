package dev.pack.modules.savingsUser;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsUserDto {

    @NotEmpty(message = "Title is required")
    private String title;

    @Max(value = 10000000, message = "Collected plans is only up to 10.000.000!")
    private Long collectedPlans;
}
