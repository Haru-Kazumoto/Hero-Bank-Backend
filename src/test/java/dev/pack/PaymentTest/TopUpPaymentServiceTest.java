package dev.pack.PaymentTest;

import dev.pack.enums.PaymentOutlets;
import dev.pack.modules.payment.PaymentService;
import dev.pack.modules.payment.Payments;
import dev.pack.modules.payment.TopUpPaymentRepository;
import dev.pack.modules.walletUser.WalletUser;
import dev.pack.modules.walletUser.WalletUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TopUpPaymentServiceTest {

    @Mock
    WalletUserRepository walletUserRepository;

    @Mock
    TopUpPaymentRepository topUpPaymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @Test
    public void testTopUpBalance() {
        // Mocking the request payment
        Payments.TopUpPaymentRequest requestPayment = new Payments.TopUpPaymentRequest();
        requestPayment.setWalletId("114966188742");
        requestPayment.setAmount(100000L);
        requestPayment.setOutlet(PaymentOutlets.ALFAMART);

        // Mocking the wallet user
        WalletUser walletUser = new WalletUser();
        walletUser.setUserBalance(100000L);

        Optional<WalletUser> optionalWalletUser = Optional.of(walletUser);
        Mockito.when(
                walletUserRepository.findByWalletId(requestPayment.getWalletId())
        ).thenReturn(optionalWalletUser);

        // Mocking the top-up receipt
        Payments.TopUpPaymentReceipt topUpReceipt = new Payments.TopUpPaymentReceipt();

        // Mocking the repository save method
        Mockito.when(
                walletUserRepository.save(Mockito.any())
        ).thenReturn(walletUser);
        Mockito.when(
                topUpPaymentRepository.save(Mockito.any())
        ).thenReturn(topUpReceipt);

        // Calling the topUpBalance method
        Payments.TopUpPaymentReceipt response = paymentService.topUpBalance(requestPayment);

        // Verifying the result
        Assertions.assertEquals(200000L, walletUser.getUserBalance());
        Assertions.assertEquals(PaymentOutlets.ALFAMART, response.getOutletName());
        Assertions.assertEquals(100000L, response.getTotalTopUpAmount());
    }
}
