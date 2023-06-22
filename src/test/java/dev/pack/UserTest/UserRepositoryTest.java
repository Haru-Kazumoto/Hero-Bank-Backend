package dev.pack.UserTest;

import dev.pack.Module.User.UserEntity;
import dev.pack.Module.User.UserRepository;
import dev.pack.Module.UserInfo.UserInfo;
import dev.pack.Module.WalletUser.WalletUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateUser() {
        //Arrange
        UserEntity user = UserEntity.builder()
                .pin("141827")
                .email("TestingEmail@gmail.com")
                .build();

        //Act
        UserEntity savedUser = userRepository.save(user);

        //Assert
        assertThat(savedUser).isNotNull();
        assertNotNull(savedUser.getId());
    }

    @Test
    void shouldGenerateRandomNumberForAccountNumber() {
        String generateRandomStringNumber = generateAccountNumber();

        assertThat(generateRandomStringNumber).isNotNull();
        assertEquals(12,generateRandomStringNumber.length());
    }

    @Test
    void shouldCreateUserAndTheRelationObject() {
        //Arrange
        UserEntity user = new UserEntity();
        user.setPin("123456");
        user.setEmail("TestingEmail@gmail.com");

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("Testing");
        userInfo.setAccountNumber(generateAccountNumber());
        userInfo.setJoinAt(new Date());
        userInfo.setPhoneNumber("4123423423");
        userInfo.setUserEntityId(user);

        WalletUser walletUser = new WalletUser();
        walletUser.setPocketBalance(BigInteger.valueOf(100000));
        walletUser.setUserBalance(BigInteger.valueOf(1000000));
        walletUser.setUserEntityId(user);

        user.setUserInfo(userInfo);
        user.setWalletUser(walletUser);

        //Act
        userRepository.save(user);

        //Assert
        assertThat(user.getId()).isNotNull();

        //Check is the data successfully created
        assertThat(userInfo.getId()).isNotNull();
        assertThat(walletUser.getId()).isNotNull();

        //Check the id relation is the relation correct
        assertEquals(user, userInfo.getUserEntityId());
        assertEquals(user, walletUser.getUserEntityId());
    }

    @Test
    void shouldUpdateUserRecordById() {
        UserEntity user = new UserEntity();
        user.setPin("214384");
        user.setEmail("TestingEmail@gmail.com");

        //Act
        userRepository.save(user);
        assertEquals("214384", user.getPin());

        UserEntity userSave = userRepository.findById(user.getId()).get();

        userSave.setPin("129382");
        user.setEmail("UpdatedEmail@gmail.com");

        UserEntity updatedData = userRepository.save(userSave);

        //Asserts
        assertThat(updatedData).isNotNull();
        assertEquals("129382", updatedData.getPin());
    }

    @Test
    void shouldDeleteUserRecordById() {
        UserEntity user = new UserEntity();
        user.setPin("214384");
        user.setEmail("TestingEmail@gmail.com");

        userRepository.save(user);
        assertThat(user.getId()).isNotNull();

        userRepository.deleteById(user.getId());
        Optional<UserEntity> returnVal = userRepository.findById(user.getId());

        assertThat(returnVal).isEmpty();

    }

    public String generateAccountNumber() {
        Random random_number = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randomDigit = random_number.nextInt(9) + 1;
            builder.append(randomDigit);
        }

        return builder.toString();
    }
}
