package dev.pack.modules.payment;

import dev.pack.enums.PaymentOutlets;
import dev.pack.exception.PaymentBalanceErrorException;
import dev.pack.modules.walletUser.WalletUser;
import dev.pack.modules.walletUser.WalletUserRepository;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WalletUserRepository walletUserRepository;
    private final TopUpPaymentRepository topUpPaymentRepository;
    @Value("${wallet-user.balance.request.max}")
    Long MAX_REQUEST_BALANCE;

    @Value("${wallet-user.balance.request.min}")
    Long MIN_REQUEST_BALANCE;

    @Transactional(rollbackOn = {
            TransactionalException.class,
            NullPointerException.class,
            PaymentBalanceErrorException.class,
            NoSuchElementException.class
    })
    public Payments.TopUpPaymentReceipt topUpBalance(Payments.TopUpPaymentRequest requestPayment){
        try{
            //This condition has a bug.
//            if(requestPayment.getAmount() > MAX_REQUEST_BALANCE){
//                throw new PaymentBalanceErrorException(String.format("Maximum top up reached! (%s)", requestPayment.getAmount()));
//            }
//            if(requestPayment.getAmount() < MIN_REQUEST_BALANCE){
//                throw new PaymentBalanceErrorException(String.format("Minimum top up reached! (%s)", requestPayment.getAmount()));
//            }

            WalletUser walletUser = walletUserRepository
                    .findByWalletId(requestPayment.getWalletId())
                    .orElseThrow(() -> new NoSuchElementException("Wallet id not found!"));

            Long currentBalance = walletUser.getUserBalance();
            Long topUpAmount = requestPayment.getAmount();
            Long resultTopUpPaymentBalance = currentBalance + topUpAmount;

            walletUser.setUserBalance(resultTopUpPaymentBalance);

            walletUserRepository.save(walletUser); //Add balance user to wallet user.

            Payments.TopUpPaymentReceipt response = new Payments.TopUpPaymentReceipt();
            response.setOutletName(requestPayment.getOutlet());
            response.setTotalTopUpAmount(requestPayment.getAmount());

            topUpPaymentRepository.save(response); //Store the history payment.

            return response;
        } catch (TransactionalException exception){
            throw new TransactionalException(exception.getMessage(), exception.getCause());
        }
    }



}
