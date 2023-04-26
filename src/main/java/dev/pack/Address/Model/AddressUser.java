package dev.pack.Address.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Builder
public class AddressUser {

    @Id @GeneratedValue(generator = "uuid")
    private UUID id;

    private String country;
    private String province;
    private String city;
    private String district;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfo_id", updatable = false)
    private UserInfo userInfo;
}