package dev.pack.User.Service.Implements;

import dev.pack.Address.Model.AddressUser;
import dev.pack.User.Model.UserEntity;
import dev.pack.User.Repository.UserRepository;
import dev.pack.User.Response.OutputResponse;
import dev.pack.User.Response.OutputResponseMapper;
import dev.pack.User.Service.Interfaces.UserService;
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
        String randomStringNumberForAccountNumber = userInfoService.generateRandomNumberForAccountAndPocketNumber();
        String randomStringNumberForPocketNumber = userInfoService.generateRandomNumberForAccountAndPocketNumber();

        //Validating the pin
        Optional<UserEntity> pin = userRepository.findByPin(user.getPin());
        if(pin.isPresent()){
            throw new DataIntegrityViolationException(
                    String.format("Pin [%s] already exists, get another pin.", user.getPin())
            );
        }
        userInfoService.findEmailUser(user.getUserInfo().getEmail()); //Validating the email from user info
        //End validating.

        //Set the random string number
        user.getUserInfo().setAccountNumber(randomStringNumberForAccountNumber);
        user.getUserInfo().setPocketNumber(randomStringNumberForPocketNumber);
        user.getUserInfo().setUserEntity(user);

        //Set addressUser body with body builder from Lombok.
        user.getUserInfo().setAddressUser(
                AddressUser.builder()
                        .id(user.getUserInfo().getId())
                        .country(user.getUserInfo().getAddressUser().getCountry())
                        .province(user.getUserInfo().getAddressUser().getProvince())
                        .city(user.getUserInfo().getAddressUser().getCity())
                        .district(user.getUserInfo().getAddressUser().getDistrict())
                        .userInfo(user.getUserInfo())
                        .build()
        );

//        user.setWalletUser(
//                WalletUser.builder()
//                        .id(user.getWalletUser().getId())
//                        .userBalance(user.getWalletUser().getUserBalance())
//                        .pocketBalance(user.getWalletUser().getPocketBalance())
//                        .userEntity(user)
//                        .build()
//        );

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
