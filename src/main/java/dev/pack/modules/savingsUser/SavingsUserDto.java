package dev.pack.modules.savingsUser;

import dev.pack.modules.user.UserEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsUserDto {

    @NotEmpty(message = "Title is required")
    private String title;

    @Max(value = 50000000, message = "Collected plans is only up to Rp10.000.000!")
    @Min(value = 100000, message = "Collected plans minimum balance is Rp100.000")
    private Long collectedPlans;

    @NotNull(message = "User id is required for relation")
    private UUID userId;
}
