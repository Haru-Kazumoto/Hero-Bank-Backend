package dev.pack.User.Service.Implements;

import dev.pack.User.Model.UserEntity;
import dev.pack.User.Repository.UserRepository;
import dev.pack.User.Response.OutputResponse;
import dev.pack.User.Response.OutputResponseMapper;
import dev.pack.User.Service.Interfaces.UserService;
import dev.pack.UserInfo.Service.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OutputResponseMapper outputResponseMapper;
    private final UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        return userRepository
                .findByPin(pin)
                .orElseThrow(() -> new NoSuchElementException("Pin user not found."));
    }

    @Override
    @Transactional(rollbackOn = {DataIntegrityViolationException.class})
    public UserEntity createUser(UserEntity user) {
        Optional<UserEntity> isPinExists = userRepository.findByPin(user.getPin());
        if(isPinExists.isPresent()){
            throw new DataIntegrityViolationException(
                    String.format("Pin [%s] already exists, get another pin.", user.getPin())
            );
        }
//        userInfoService.findEmailUser(user.getInfo().getEmail());
        return userRepository.save(user);
    }

    @Override
    public List<OutputResponse> getAllUser() {
        return userRepository
                .findAll()
                .stream()
                .map(outputResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = {NoSuchElementException.class})
    public void deleteUserById(UUID id) {
        userRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Id %s not exists", id)));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = {
            NoSuchElementException.class,
            DataIntegrityViolationException.class
    })
    public UserEntity updateUser(UUID id, UserEntity user) {
        return null;
    }
}
