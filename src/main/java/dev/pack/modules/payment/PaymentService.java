package dev.pack.modules.payment;

import dev.pack.enums.PaymentOutlets;
import dev.pack.exception.PaymentBalanceErrorException;
import dev.pack.exception.UnsupportedPaymentPlatformException;
import dev.pack.modules.walletUser.WalletUser;
import dev.pack.modules.walletUser.WalletUserRepository;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final WalletUserRepository walletUserRepository;
    private final TopUpPaymentHistoryRepository topUpPaymentHistoryRepository;

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
    public Payments.TopUpPaymentHistory topUpBalance(Payments.TopUpPaymentRequest requestPayment){
        try{
            WalletUser walletUser = walletUserRepository
                    .findByWalletId(requestPayment.getWalletId())
                    .orElseThrow(() -> new NoSuchElementException("Wallet id not found!"));

            Long currentBalanceUser = walletUser.getUserBalance();
            Long topUpAmount = requestPayment.getAmount();
            Long resultTopUpPaymentBalance = currentBalanceUser + topUpAmount;

            validateAmountRequest(topUpAmount);

            //Check the outlet is the outlet support for hero bank payment
            if(!Arrays.asList(PaymentOutlets.values()).contains(requestPayment.getOutlet())){
                throw new UnsupportedPaymentPlatformException("The platform doesn't support for hero bank payment!");
            }

            walletUser.setUserBalance(resultTopUpPaymentBalance);
            walletUserRepository.save(walletUser); //Add balance user to wallet user.

            Payments.TopUpPaymentHistory response = new Payments.TopUpPaymentHistory();
            response.setOutletName(requestPayment.getOutlet());
            response.setTotalTopUpAmount(requestPayment.getAmount());

            topUpPaymentHistoryRepository.save(response); //Store the history payment.

            return response;
        } catch (TransactionalException exception){
            throw new TransactionalException(exception.getMessage(), exception.getCause());
        }
    }


    @Transactional(rollbackOn = PaymentBalanceErrorException.class)
    public void validateAmountRequest(Long amount){
        if(amount > MAX_REQUEST_BALANCE){
            throw new PaymentBalanceErrorException("Maximum top up balance value reached!");
        } else if(amount < MIN_REQUEST_BALANCE) {
            throw new PaymentBalanceErrorException("Minimum top up balance value reached!");
        }
    }
}
