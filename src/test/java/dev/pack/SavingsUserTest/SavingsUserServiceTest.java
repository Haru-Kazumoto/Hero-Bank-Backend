package dev.pack.SavingsUserTest;

import dev.pack.modules.savingsUser.SavingsUser;
import dev.pack.modules.savingsUser.SavingsUserRepository;
import dev.pack.modules.savingsUser.SavingsUserServiceImpl;
import dev.pack.modules.user.UserEntity;
import dev.pack.modules.user.UserRepository;
import dev.pack.utils.Generate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SavingsUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private SavingsUserRepository savingsUserRepository;
    @InjectMocks
    private SavingsUserServiceImpl savingsUserService;
    @InjectMocks
    private Generate generate;

    UserEntity users = new UserEntity();
    List<SavingsUser> savingsUsers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        users = UserEntity.builder()
                .pin("123456")
                .email("TestingEmail@gmail.com")
                .accountId(generate.randomIdNumber(15))
                .savingsUsers(savingsUsers)
                .build();

        SavingsUser savingsUser1 = SavingsUser.builder()
                .title("Savings user 1")
                .userId(users.getId())
                .savingsBalance(0L)
                .collectedPlans(0L)
                .collectedPlans(100000L)
                .userEntityId(users)
                .build();

        userRepository.save(users);
        savingsUsers.add(savingsUser1);
    }

    @Test
    void shouldGetSavingsUserByAccountIdUser() {

    }
}
