package dev.pack.UserTest;

import dev.pack.modules.user.UserEntity;
import dev.pack.modules.user.UserRepository;
import dev.pack.modules.user.UserServiceImpl;
import dev.pack.modules.userInfo.UserInfo;
import dev.pack.modules.walletUser.WalletUser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @Disabled
    void shouldCreateUserWithTheRelationObject() {
        //Arrange
        UserEntity user = new UserEntity();
        user.setPin("123456");
        user.setEmail("TestingEmail@gmail.com");

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("Testing");
        userInfo.setJoinAt(new Date());
        userInfo.setPhoneNumber("4123423423");
        userInfo.setUserEntityId(user);

        WalletUser walletUser = new WalletUser();
        walletUser.setPocketBalance(1000000L);
        walletUser.setUserBalance(1000000L);
        walletUser.setUserEntityId(user);

        user.setUserInfo(userInfo);
        user.setWalletUser(walletUser);

        //Act
        //Mock behavior for passwordEncoder
        BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        Mockito
                .when(passwordEncoder.encode(Mockito.any(CharSequence.class)))
                        .thenReturn("encodedPassword");

//        userService.setPasswordEncoder(passwordEncoder);

        Mockito
                .when(userRepository.save(user))
                .thenAnswer(
                        (invocation) -> invocation.<UserEntity>getArgument(0)
                );

        UserEntity savedUser = userService.createUserBody(user);

        //Asserts
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserInfo()).isNotNull();
        assertThat(savedUser.getSavingsUsers()).isNotNull();
        assertThat(savedUser.getWalletUser()).isNotNull();
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
