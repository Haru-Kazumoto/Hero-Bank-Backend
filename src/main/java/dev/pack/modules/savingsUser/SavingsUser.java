package dev.pack.modules.savingsUser;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID userId; //Untuk merepresentasikan id dari user.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEntity_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserEntity userEntityId;

    @PrePersist
    public void setSavingsBalance() {
        this.savingsBalance = 0L;
    }
}
