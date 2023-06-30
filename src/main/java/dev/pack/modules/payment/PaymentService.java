package dev.pack.modules.payment;

import dev.pack.exception.MaximumValueBalanceException;
import dev.pack.exception.MinimumValueBalanceException;
import dev.pack.modules.walletUser.WalletUserRepository;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WalletUserRepository walletUserRepository;

    @Transactional(rollbackOn = {
            TransactionalException.class,
            MaximumValueBalanceException.class,
            MinimumValueBalanceException.class,
            NullPointerException.class,
    })
    public Payments.PaymentReceiptResponse topUpPayment(Payments.TopUpPaymentRequest requestPayment){
        return null;
    }



}
