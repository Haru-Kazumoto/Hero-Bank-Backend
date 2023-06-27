package dev.pack.modules.savingsUser;

import dev.pack.modules.user.UserEntity;
import jakarta.validation.constraints.Max;
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

    @Max(value = 10000000, message = "Collected plans is only up to 10.000.000!")
    private Long collectedPlans;

    @NotNull(message = "User id is required for relation")
    private UUID userId;
}
