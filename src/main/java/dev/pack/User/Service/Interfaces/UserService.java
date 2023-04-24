package dev.pack.User.Service.Interfaces;

import dev.pack.User.Model.UserEntity;
import dev.pack.User.Response.OutputResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserEntity createUser(UserEntity user);

    List<OutputResponse> getAllUser();

    void deleteUserById(UUID id);

    UserEntity updateUser(UUID id, UserEntity user);
}
