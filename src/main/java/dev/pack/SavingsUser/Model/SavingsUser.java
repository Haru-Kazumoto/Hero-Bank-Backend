package dev.pack.SavingsUser.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.pack.User.Model.UserEntity;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class SavingsUser {

    @Id @GeneratedValue(generator = "uuid")
    private UUID id;

    private String title;
    private BigInteger savingsBalance;
    private BigInteger collectedPlans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntityId;

    @PrePersist
    public void setSavingsBalance() {
        this.savingsBalance = BigInteger.valueOf(0);
    }
}
