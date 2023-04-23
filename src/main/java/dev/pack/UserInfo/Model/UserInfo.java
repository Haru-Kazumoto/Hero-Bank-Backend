package dev.pack.UserInfo.Model;

import dev.pack.Address.Model.AddressUser;
import dev.pack.User.Model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "User_Info_Table")
/**
 * UserInfo {parent for user addressUser, child for UserEntity}
 */
public class UserInfo {
    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.AUTO)
    private UUID id;

    private Long pocketNumber;
    private Long accountNumber;
    @Column(unique = true, nullable = false)
    private String email;
    private String officialName;
    private String nickname;
    private LocalDate joinAt;

    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private AddressUser addressUser;

    @OneToOne
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;


}
