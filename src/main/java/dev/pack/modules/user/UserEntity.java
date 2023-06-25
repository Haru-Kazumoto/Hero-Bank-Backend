package dev.pack.modules.user;

import com.fasterxml.jackson.annotation.*;
import dev.pack.modules.savingsUser.SavingsUser;
import dev.pack.modules.userInfo.UserInfo;
import dev.pack.modules.walletUser.WalletUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "User_Table")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue( generator = "uuid")
    private UUID id;

    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Exclude field to write only
    private String pin;

    @OneToOne(
            mappedBy = "userEntityId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private UserInfo userInfo;

    @OneToOne(
            mappedBy = "userEntityId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private WalletUser walletUser;

    @OneToMany(
            mappedBy = "userEntityId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SavingsUser> savingsUsers = new ArrayList<>();

    @PrePersist
    public void setWalletUser() {
        if(this.walletUser == null){
            this.walletUser = new WalletUser();
            this.walletUser.setUserBalance(BigInteger.valueOf(0));
            this.walletUser.setPocketBalance(BigInteger.valueOf(0));
            this.walletUser.setUserEntityId(this);
        }
    }

    //Authority is not used in this app
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.pin;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
