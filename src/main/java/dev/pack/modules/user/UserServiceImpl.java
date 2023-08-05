package dev.pack.modules.user;

import dev.pack.modules.payment.topup.TopUpPaymentHistoryRepository;
import dev.pack.modules.userInfo.UserInfoServiceImpl;
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
    private final UserInfoServiceImpl userInfoServiceImpl;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Generate generate;
    private final TopUpPaymentHistoryRepository topUpPaymentHistoryRepository;

    /**
     * Find user by they pin, and then it'll return the object of userDetails.
     *
     * @param pin
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        return userRepository
                .findByPin(pin)
                .orElseThrow(() -> new NoSuchElementException("Pin user not found."));
    }

    /**
     * This method for registering user in auth.
     * First it will encode the password, and then set the account id with random generator.
     * Then create the user info object by .createUserInfoBody();
     * also set the wallet id with the same random generator
     * and then it will return the userEntity object and the relational object tho.
     *
     * If the register has some exception it will auto rollback with @Transactional annotation,
     * check what's the exception that cause the rollback on the rollbackOn={}.
     *
     *
     * @param user
     * @return UserEntity
     */
    @Override
    @Transactional(rollbackOn = {
            DataIntegrityViolationException.class,
            SQLException.class,
            DuplicateKeyException.class,
            NullPointerException.class
    })
    public UserEntity createUserBody(UserEntity user) {
        user.setPin(passwordEncoder.encode(user.getPassword()));
        user.setAccountId(generate.randomIdNumber(15));
        userInfoServiceImpl.createUserInfoBody(user);
        user.getWalletUser().setWalletId(generate.randomIdNumber(12));
        return userRepository.save(user);
    }

    /**
     * Get all the user that has created.
     * Exclude : pin
     *
     * @return List of UserEntity
     */
    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Deleting user by id and then it will find the id and delete them,
     * but if not found (.isEmpty) it will return NoSuchElementException.
     *
     * @param id
     * @return Map with key String and value String
     */
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

    /**
     * Custom response for delete user by id.
     *
     * @param id
     * @param status
     * @param message
     * @return Map with key String and value String
     */
    private Map<String, String> setResponse(UUID id, HttpStatus status, String message) {
        Map<String, String> response = new HashMap<>();
        response.put("id", id.toString());
        response.put("status", status.toString());
        response.put("message", message);

        return response;
    }

    /**
     * Updating user with the id of user, that has rollback system.
     *
     * @param id
     * @param user
     * @return UserEntity
     */
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
