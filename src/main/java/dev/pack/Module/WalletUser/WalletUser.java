package dev.pack.Module.WalletUser;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.pack.Module.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Wallet_User_Table")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class WalletUser {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private BigInteger userBalance;
    private BigInteger pocketBalance;

    @JsonIgnoreProperties(
            {
                    "hibernateLazyInitializer",
                    "handler"
            })
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            updatable = false
    )
    private UserEntity userEntityId;
}