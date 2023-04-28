package dev.pack.User.Service.Implements;

import dev.pack.Address.Model.AddressUser;
import dev.pack.Address.Service.AddressUserService;
import dev.pack.SavingsUser.Model.SavingsUser;
import dev.pack.SavingsUser.Repository.SavingsUserRepository;
import dev.pack.User.Model.UserEntity;
import dev.pack.User.Repository.UserRepository;
import dev.pack.User.Response.OutputResponse;
import dev.pack.User.Response.OutputResponseMapper;
import dev.pack.User.Service.Interfaces.UserService;
import dev.pack.User.Validator.UserValidator;
import dev.pack.UserInfo.Model.UserInfo;
import dev.pack.UserInfo.Service.UserInfoService;
import dev.pack.WalletUser.Model.WalletUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OutputResponseMapper outputResponseMapper;
    private final UserInfoService userInfoService;
    private final SavingsUserRepository savingsUserRepository;
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
        //Create a random string number for number account and number pocket user
        String randomNumber = userInfoService.generateNumberForAccountNumber();

        userValidator.validate(user);

        userInfoService.findEmailUser(user.getUserInfo().getEmail());
        userInfoService.findPhoneNumberUser(user.getUserInfo().getPhoneNumber());
        //End validating.

        //Set the random string number
        user.getUserInfo().setAccountNumber(randomNumber);
        user.getUserInfo().setUserEntityId(user);

//        addressUserService.createAddressUser(user
//                .getUserInfo()
//                .getAddressUser()
//        );

        //Set addressUser body with body builder from Lombok.
        user.getUserInfo().setAddressUser(
                AddressUser.builder()
                        .id(user.getUserInfo().getId())
                        .country(user.getUserInfo().getAddressUser().getCountry())
                        .province(user.getUserInfo().getAddressUser().getProvince())
                        .city(user.getUserInfo().getAddressUser().getCity())
                        .district(user.getUserInfo().getAddressUser().getDistrict())
                        .userInfoId(user.getUserInfo())
                        .build()
        );
//        UserInfo userInfoBody = userInfoService.createAddressUser(user.getUserInfo());
//        user.setUserInfo(userInfoBody);

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
