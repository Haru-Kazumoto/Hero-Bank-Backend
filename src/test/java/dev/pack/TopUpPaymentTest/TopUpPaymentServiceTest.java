package dev.pack.TopUpPaymentTest;

import dev.pack.enums.PaymentOutlets;
import dev.pack.modules.payment.topup.TopUpPaymentService;
import dev.pack.modules.payment.topup.TopUpPaymentHistoryRepository;
import dev.pack.modules.payment.topup.TopUpPayments;
import dev.pack.modules.user.UserEntity;
import dev.pack.modules.user.UserRepository;
import dev.pack.modules.user.UserService;
import dev.pack.modules.user.UserServiceImpl;
import dev.pack.modules.userInfo.UserInfo;
import dev.pack.modules.walletUser.WalletUser;
import dev.pack.modules.walletUser.WalletUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TopUpPaymentServiceTest {

    @Mock WalletUserRepository walletUserRepository;
    @Mock TopUpPaymentHistoryRepository topUpPaymentHistoryRepository;
    @Mock UserRepository userRepository;
    @InjectMocks TopUpPaymentService topUpPaymentService;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @Disabled
    public void testTopUpBalance() {
        // Mocking the request payment
        TopUpPayments.TopUpPaymentRequest requestPayment = new TopUpPayments.TopUpPaymentRequest();
        requestPayment.setAmount(100000L);
        requestPayment.setOutlet(PaymentOutlets.ALFAMART);
        requestPayment.setAccountId("787975653831459");
        requestPayment.setWalletId("385772121161");

        // Mocking the wallet user
        WalletUser walletUser = new WalletUser();
        walletUser.setUserBalance(100000L);

        Optional<WalletUser> optionalWalletUser = Optional.of(walletUser);
        Mockito.when(walletUserRepository.findByWalletId(requestPayment.getWalletId())).thenReturn(optionalWalletUser);

        // Mocking the top-up receipt
        TopUpPayments.TopUpPaymentHistory topUpHistory = new TopUpPayments.TopUpPaymentHistory();

        // Mocking the repository save method
        Mockito.when(walletUserRepository.save(Mockito.any())).thenReturn(walletUser);
        Mockito.when(topUpPaymentHistoryRepository.save(Mockito.any())).thenReturn(topUpHistory);

        // Calling the topUpBalance method
        TopUpPayments.TopUpPaymentHistory response = topUpPaymentService.topUpBalance(requestPayment);

        // Verifying the result
        Assertions.assertEquals(200000L, walletUser.getUserBalance());
        Assertions.assertEquals(PaymentOutlets.INDOMART, response.getOutletName());
        Assertions.assertEquals(100000L, response.getTotalTopUpAmount());
    }

    @Test
    public void findHistoryTopUpByPaymentId(){
        TopUpPayments.TopUpPaymentRequest requestPayment = new TopUpPayments.TopUpPaymentRequest();
        requestPayment.setAmount(100000L);
        requestPayment.setOutlet(PaymentOutlets.ALFAMART);
        requestPayment.setAccountId("787975653831459");
        requestPayment.setWalletId("385772121161");

        // Mocking the wallet user
        WalletUser walletUser = new WalletUser();
        walletUser.setUserBalance(100000L);

        Optional<WalletUser> optionalWalletUser = Optional.of(walletUser);
        Mockito.when(walletUserRepository.findByWalletId(requestPayment.getWalletId())).thenReturn(optionalWalletUser);

        // Mocking the top-up receipt
        TopUpPayments.TopUpPaymentHistory topUpHistory = new TopUpPayments.TopUpPaymentHistory();

        // Mocking the repository save method
        Mockito.when(walletUserRepository.save(Mockito.any())).thenReturn(walletUser);
        Mockito.when(topUpPaymentHistoryRepository.save(Mockito.any())).thenReturn(topUpHistory);

        // Calling the topUpBalance method
//        TopUpPayments.TopUpPaymentHistory response = userService.find(topUpHistory.getPaymentId());

//        Assertions.assertEquals(topUpHistory, response);
    }
}
