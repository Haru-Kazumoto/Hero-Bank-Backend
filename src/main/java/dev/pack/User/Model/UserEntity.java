package dev.pack.User.Model;

import dev.pack.UserInfo.Model.UserInfo;
import dev.pack.WalletUser.Model.WalletUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    private UUID id;

    @Column(unique = true, nullable = false)
    private Long pin;

    private boolean isActive;
    private boolean isLocked;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private UserInfo info;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<WalletUser> walletUsers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.pin.toString();
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
