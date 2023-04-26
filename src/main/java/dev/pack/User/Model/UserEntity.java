package dev.pack.User.Model;

import com.fasterxml.jackson.annotation.*;
import dev.pack.UserInfo.Model.UserInfo;
import dev.pack.WalletUser.Model.WalletUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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

    @Column(unique = true, nullable = false)
    private String pin;

    @OneToOne(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private UserInfo userInfo;

    @OneToOne(
            mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private WalletUser walletUser;

    @PrePersist
    public void setWalletUser() {
        this.walletUser = new WalletUser();
        this.walletUser.setUserEntity(this);
        this.walletUser.setUserBalance(BigInteger.valueOf(0));
        this.walletUser.setPocketBalance(BigInteger.valueOf(0));
    }

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
        return null;
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
