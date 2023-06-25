package dev.pack.modules.savingsUser;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.pack.modules.user.UserEntity;
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
    private Long savingsBalance;
    private Long collectedPlans;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntityId;

    @PrePersist
    public void setSavingsBalance() {
        this.savingsBalance = 0L;
    }
}