package dev.pack.modules.user;

import dev.pack.exception.IdNotFoundException;
import dev.pack.modules.payment.topup.TopUpPaymentHistoryRepository;
import dev.pack.modules.payment.topup.TopUpPayments;
import dev.pack.modules.userInfo.UserInfoInfoServiceImpl;
import dev.pack.utils.Generate;
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
    private final UserInfoInfoServiceImpl userInfoServiceImpl;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Generate generate;
    private final TopUpPaymentHistoryRepository topUpPaymentHistoryRepository;

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
                    SQLException.class,
                    DuplicateKeyException.class,
                    NullPointerException.class
            })
    public UserEntity createUserBody(UserEntity user){
        user.setPin(
                passwordEncoder.encode(user.getPassword()) //Hash pin
        );

        user.setAccountId(
                generate.randomIdNumber(15) //Generating random number
        );

        userInfoServiceImpl.createUserInfoBody(user); //Creating user info body object

        user.getWalletUser().setWalletId(generate.randomIdNumber(12)); //Generate random wallet id

        return userRepository.save(user); //Savings all user body and those relational object though
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
                "Success to delete."
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

    //TODO : create method change store balance user to savings user.
    //TODO : create method change store balance user to pocket user.
    //TODO : create method that could take balance from savings.
}
