package dev.pack.User.Service.Implements;

import dev.pack.Address.Service.AddressUserService;
import dev.pack.SavingsUser.Model.SavingsUser;
import dev.pack.User.Model.UserEntity;
import dev.pack.User.Repository.UserRepository;
import dev.pack.User.Service.Interfaces.UserService;
import dev.pack.User.Validator.UserValidator;
import dev.pack.UserInfo.Model.UserInfo;
import dev.pack.UserInfo.Service.UserInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoService userInfoService;
    private final UserValidator userValidator;
    private final AddressUserService addressUserService;

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
    public UserEntity createUser(UserEntity user) {

        UserInfo userInfo = user.getUserInfo();
        List<SavingsUser> savingsUserList = user.getSavingsUsers();
        //Create a random string number for number account and number pocket user
        String randomNumber = userInfoService.generateNumberForAccountNumber();

        userValidator.validate(user);

        userInfoService.findEmailUser(userInfo.getEmail());
        userInfoService.findPhoneNumberUser(userInfo.getPhoneNumber());
        //End validating.

        //Set the random string number
        userInfo.setAccountNumber(randomNumber);
        userInfo.setUserEntityId(user);

        //Set addressUser body.
        userInfo.setAddressUser(
                addressUserService.createAddressUser(
                        user.getUserInfo()
                )
        );

        for (SavingsUser savingsUser : savingsUserList) {
            savingsUser.setUserEntityId(user);
        }
        user.setSavingsUsers(savingsUserList);

        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
//    @Transactional(rollbackOn = {NoSuchElementException.class})
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
