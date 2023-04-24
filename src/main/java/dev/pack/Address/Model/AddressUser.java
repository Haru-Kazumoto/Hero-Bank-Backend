package dev.pack.Address.Model;

import dev.pack.UserInfo.Model.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "address_user_table")
public class AddressUser {

    @Id @GeneratedValue(generator = "uuid")
    private UUID id;

    private String country;
    private String province;
    private String city;
    private String district;

    @OneToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;
}