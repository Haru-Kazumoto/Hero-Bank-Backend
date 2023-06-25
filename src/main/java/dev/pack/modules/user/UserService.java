package dev.pack.modules.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserEntity createUserBody(UserEntity user);

    List<UserEntity> getAllUser();

    Map<String, String> deleteUserById(UUID id);

    UserEntity updateUser(UUID id, UserEntity user);
}
