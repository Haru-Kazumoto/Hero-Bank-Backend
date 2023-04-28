package dev.pack.SavingsUser.Model;

import dev.pack.User.Model.UserEntity;
import dev.pack.WalletUser.Model.WalletUser;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "savings_user_table")
public class SavingsUser {

    @Id @GeneratedValue(generator = "uuid")
    private UUID id;

    private String title;
    private BigInteger savingsBalance = BigInteger.valueOf(0);
    private BigInteger collectedPlans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntityId;
}
