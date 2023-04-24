package dev.pack.WalletUser.Model;

import dev.pack.User.Model.UserEntity;
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
public class WalletUser {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private BigInteger userBalance;
    private BigInteger pocketBalance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
