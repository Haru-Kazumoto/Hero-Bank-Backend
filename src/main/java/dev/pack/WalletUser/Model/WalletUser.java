package dev.pack.WalletUser.Model;

import dev.pack.User.Model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    private UUID id;
    private Long userBalance;
    private Long pocketBalance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
