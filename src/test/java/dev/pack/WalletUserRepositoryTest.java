package dev.pack;

import dev.pack.modules.walletUser.WalletUser;
import dev.pack.modules.walletUser.WalletUserRepository;
import dev.pack.utils.Generate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class WalletUserRepositoryTest {

    @Autowired
    private WalletUserRepository walletUserRepository;

    WalletUser walletUser = new WalletUser();

    @BeforeEach
    void setUp() {
        walletUser = WalletUser.builder()
                .userBalance(1000000L)
                .pocketBalance(0L)
                .walletId(randomIdNumber(12))
                .build();
    }

    @Test
    void shouldReturnWalletUser() {
        walletUserRepository.save(walletUser);
        WalletUser findWalletUserByWalletId = walletUserRepository
                .findByWalletId(walletUser.getWalletId())
                .orElseThrow(() -> new NoSuchElementException("Wallet id not found."));

        System.out.println(findWalletUserByWalletId);
        assertThat(findWalletUserByWalletId).isNotNull();
        assertThat(findWalletUserByWalletId.getWalletId()).isNotNull();

    }

    public String randomIdNumber(int num) {
        Random random_number = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < num; i++) {
            int randomDigit = random_number.nextInt(9) + 1;
            builder.append(randomDigit);
        }

        return builder.toString();
    }
}
