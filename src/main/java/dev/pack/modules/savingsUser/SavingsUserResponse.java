package dev.pack.modules.savingsUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingsUserResponse {
    private UUID id;
    private String title;
    private Long savingsBalance;
    private Long collectedPlans;
    private UUID userId;
}
