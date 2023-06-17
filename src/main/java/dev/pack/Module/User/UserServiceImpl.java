package dev.pack.Module.User;

import dev.pack.Module.UserInfo.UserInfoInfoServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //Service dependency
    private final UserInfoInfoServiceImpl userInfoServiceImpl;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        return userRepository
                .findByPin(pin)
                .orElseThrow(() -> new NoSuchElementException("Pin user not found."));
    }

    @Override
    @Transactional(
            rollbackOn = {
                    DataIntegrityViolationException.class,
                    RuntimeException.class,
                    Exception.class,
                    SQLException.class,
                    DuplicateKeyException.class
            })
    public UserEntity createUserBody(UserEntity user){

        userInfoServiceImpl.createUserInfoBody(user);

        user.setPin(passwordEncoder.encode(user.getPassword())); //Hash pin

        return userRepository.save(user);
    }

    private Map<String, String> setResponse(UUID id, HttpStatus status, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("id", id.toString());
        response.put("status", status.toString());
        response.put("message", message);

        return response;
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Map<String, String> deleteUserById(UUID id) {
        Optional<UserEntity> findId = userRepository.findById(id);
        if (findId.isEmpty()){
            Map<String, String> response = setResponse(
                    id,
                    HttpStatus.NOT_FOUND,
                    String.format("Id %s is not found.",id)
            );
            throw new NoSuchElementException((Throwable) response);
        }
        userRepository.deleteById(id);
        return setResponse(
                id,
                HttpStatus.OK,
                String.format("Id %s success to delete.",id)
        );
    }

    @Override
    @Transactional(rollbackOn = {
            NoSuchElementException.class,
            DataIntegrityViolationException.class
    })
    public UserEntity updateUser(UUID id, UserEntity user) {
        Optional<UserEntity> findId = userRepository.findById(id);
        if(findId.isEmpty()){
            throw new NoSuchElementException(String.format("Id %s is not found!", id));
        }

        return userRepository.save(user);
    }
}