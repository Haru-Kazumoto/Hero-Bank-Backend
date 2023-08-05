package dev.pack.TopUpPaymentTest;

import dev.pack.modules.payment.topup.TopUpPaymentServiceImpl;
import dev.pack.modules.payment.topup.TopUpPaymentHistoryRepository;
import dev.pack.modules.user.UserRepository;
import dev.pack.modules.user.UserServiceImpl;
import dev.pack.modules.walletUser.WalletUserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TopUpPaymentServiceImplTest {

    @Mock WalletUserRepository walletUserRepository;
    @Mock TopUpPaymentHistoryRepository topUpPaymentHistoryRepository;
    @Mock UserRepository userRepository;

    @InjectMocks
    TopUpPaymentServiceImpl topUpPaymentServiceImpl;
    @InjectMocks
    UserServiceImpl userService;
//
//    @Test
//    @Disabled
//    public void testTopUpBalance() {
//        // Mocking the request payment
//        TopUpPaymentsDto.TopUpPaymentRequest requestPayment = new TopUpPaymentsDto.TopUpPaymentRequest();
//        requestPayment.setAmount(100000L);
//        requestPayment.setOutlet(OutletPaymentsEnum.ALFAMART);
//        requestPayment.setAccountId("787975653831459");
//        requestPayment.setWalletId("385772121161");
//
//        // Mocking the wallet user
//        WalletUser walletUser = new WalletUser();
//        walletUser.setUserBalance(100000L);
//
//        Optional<WalletUser> optionalWalletUser = Optional.of(walletUser);
//        Mockito.when(walletUserRepository.findByWalletId(requestPayment.getWalletId())).thenReturn(optionalWalletUser);
//
//        // Mocking the top-up receipt
//        TopUpPaymentsDto.TopUpPaymentHistory topUpHistory = new TopUpPaymentsDto.TopUpPaymentHistory();
//
//        // Mocking the repository save method
//        Mockito.when(walletUserRepository.save(Mockito.any())).thenReturn(walletUser);
//        Mockito.when(topUpPaymentHistoryRepository.save(Mockito.any())).thenReturn(topUpHistory);
//
//        // Calling the topUpBalance method
//        TopUpPaymentsDto.TopUpPaymentHistory response = topUpPaymentServiceImpl.topUpBalance(requestPayment);
//
//        // Verifying the result
//        Assertions.assertEquals(200000L, walletUser.getUserBalance());
//        Assertions.assertEquals(OutletPaymentsEnum.INDOMART, response.getOutletName());
//        Assertions.assertEquals(100000L, response.getTotalTopUpAmount());
//    }
//
//    @Test
//    public void findHistoryTopUpByPaymentId(){
//        TopUpPaymentsDto.TopUpPaymentRequest requestPayment = new TopUpPaymentsDto.TopUpPaymentRequest();
//        requestPayment.setAmount(100000L);
//        requestPayment.setOutlet(OutletPaymentsEnum.ALFAMART);
//        requestPayment.setAccountId("787975653831459");
//        requestPayment.setWalletId("385772121161");
//
//        // Mocking the wallet user
//        WalletUser walletUser = new WalletUser();
//        walletUser.setUserBalance(100000L);
//
//        Optional<WalletUser> optionalWalletUser = Optional.of(walletUser);
//        Mockito.when(walletUserRepository.findByWalletId(requestPayment.getWalletId())).thenReturn(optionalWalletUser);
//
//        // Mocking the top-up receipt
//        TopUpPaymentsDto.TopUpPaymentHistory topUpHistory = new TopUpPaymentsDto.TopUpPaymentHistory();
//
//        // Mocking the repository save method
//        Mockito.when(walletUserRepository.save(Mockito.any())).thenReturn(walletUser);
//        Mockito.when(topUpPaymentHistoryRepository.save(Mockito.any())).thenReturn(topUpHistory);
//
//        // Calling the topUpBalance method
////        TopUpPayments.TopUpPaymentHistory response = userService.find(topUpHistory.getPaymentId());
//
////        Assertions.assertEquals(topUpHistory, response);
//    }
}
