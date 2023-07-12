package dev.pack.modules.payment.topup;

import dev.pack.exception.IdNotFoundException;
import dev.pack.exception.PaymentBalanceErrorException;
import dev.pack.modules.user.UserRepository;
import dev.pack.modules.walletUser.WalletUser;
import dev.pack.modules.walletUser.WalletUserRepository;
import dev.pack.utils.Generate;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopUpPaymentService {

    private final WalletUserRepository walletUserRepository;
    private final TopUpPaymentHistoryRepository topUpPaymentHistoryRepository;
    private final UserRepository userRepository;
    private final Generate generate;

    @Value("${wallet-user.balance.request.max}")
    Long MAX_REQUEST_BALANCE;

    @Value("${wallet-user.balance.request.min}")
    Long MIN_REQUEST_BALANCE;

    @Transactional(rollbackOn = {
            TransactionalException.class,
            NullPointerException.class,
            PaymentBalanceErrorException.class,
            IdNotFoundException.class,
            RuntimeException.class,
            UnknownError.class
    })
    public TopUpPayments.TopUpPaymentHistory topUpBalance(TopUpPayments.TopUpPaymentRequest requestPayment){
        try{
            WalletUser walletUser = walletUserRepository
                    .findByWalletId(requestPayment.getWalletId())
                    .orElseThrow(() -> new IdNotFoundException("Wallet id user not found!"));

            Long currentBalanceUser = walletUser.getUserBalance();
            Long topUpAmount = requestPayment.getAmount();
            Long resultTopUpPaymentBalance = currentBalanceUser + topUpAmount;

            validateAmountRequest(topUpAmount);

            userRepository.findByAccountId(requestPayment.getAccountId()).orElseThrow(() -> new IdNotFoundException("Account id user not found!"));

            walletUser.setUserBalance(resultTopUpPaymentBalance); //Set the new balance

            walletUserRepository.save(walletUser); //Add balance user to wallet user.

            TopUpPayments.TopUpPaymentHistory response = new TopUpPayments.TopUpPaymentHistory();
            response.setOutletName(requestPayment.getOutlet());
            response.setTotalTopUpAmount(requestPayment.getAmount());
            response.setPaymentId(generate.randomPaymentId()); //auto generating random payment id.
            response.setAccountId(requestPayment.getAccountId());
            response.setWalletId(requestPayment.getWalletId());

            topUpPaymentHistoryRepository.save(response); //Store the history payment.

            return response;
        } catch (TransactionalException exception){
            throw new TransactionalException(exception.getMessage(), exception.getCause());
        }
    }

    public List<TopUpPayments.TopUpPaymentHistory> findHistoriesTopUpPaymentsUserByAccountIdUser(String accountId){
        List<TopUpPayments.TopUpPaymentHistory> histories = topUpPaymentHistoryRepository.findTopUpPaymentHistoriesByAccountId(accountId);
        if(histories.isEmpty()){
            throw new IdNotFoundException("Account id user is not found.");
        }
        return histories;
    }

    public TopUpPayments.TopUpPaymentHistory findHistoryTopUpPaymentUserByPaymentId(String paymentId){
        TopUpPayments.TopUpPaymentHistory history = topUpPaymentHistoryRepository.findTopUpPaymentHistoriesByPaymentId(paymentId);
        if(history == null){
            throw new IdNotFoundException("Payment id not found.");
        }

        return history;
    }

    public void validateAmountRequest(Long amount){
        if(amount > MAX_REQUEST_BALANCE){
            throw new PaymentBalanceErrorException("Maximum top up balance value reached!");
        } else if(amount < MIN_REQUEST_BALANCE) {
            throw new PaymentBalanceErrorException("Minimum top up balance value reached!");
        }
    }
}
