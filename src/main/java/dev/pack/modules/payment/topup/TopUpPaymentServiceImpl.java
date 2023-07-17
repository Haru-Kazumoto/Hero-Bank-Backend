package dev.pack.modules.payment.topup;

import dev.pack.exception.IdNotFoundException;
import dev.pack.exception.MissMatchException;
import dev.pack.exception.PaymentBalanceErrorException;
import dev.pack.modules.payment.paymentNotification.TopUpPaymentHistory;
import dev.pack.modules.payment.paymentNotification.TopUpPaymentHistoryRepository;
import dev.pack.modules.user.UserEntity;
import dev.pack.modules.user.UserRepository;
import dev.pack.modules.walletUser.WalletUser;
import dev.pack.modules.walletUser.WalletUserRepository;
import dev.pack.utils.Generate;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TopUpPaymentServiceImpl implements TopUpPaymentService{

    private final WalletUserRepository walletUserRepository;
    private final TopUpPaymentHistoryRepository topUpPaymentHistoryRepository;
    private final UserRepository userRepository;
    private final Generate generate;

    @Value("${wallet-user.balance.request.max}")
    Long MAX_REQUEST_BALANCE;

    @Value("${wallet-user.balance.request.min}")
    Long MIN_REQUEST_BALANCE;

    /**
     * When user do top up payment, it will creating history payment
     *
     * @param requestPayment
     * @return Struct top up payment from model TopUpPaymentRequest
     */
    @Transactional(rollbackOn = {
            TransactionalException.class,
            NullPointerException.class,
            PaymentBalanceErrorException.class,
            IdNotFoundException.class,
            RuntimeException.class,
            UnknownError.class
    })
    @Override
    public TopUpPaymentHistory topUpBalance(TopUpPaymentsDto.Request requestPayment){
        try{
            WalletUser walletUser = walletUserRepository
                    .findByWalletId(requestPayment.getWalletId())
                    .orElseThrow(() -> new IdNotFoundException("Wallet id user not found!"));

            Long currentBalanceUser = walletUser.getUserBalance();
            Long topUpAmount = requestPayment.getAmount();
            Long resultTopUpPaymentBalance = currentBalanceUser + topUpAmount;

            UserEntity accountIdUser = userRepository
                    .findByAccountId(requestPayment.getAccountId())
                    .orElseThrow(() -> new IdNotFoundException("Account id user not found!"));

            if(!accountIdUser.getWalletUser().getWalletId().equals(requestPayment.getWalletId())){
                throw new MissMatchException("Wallet id isn't match to the account!");
            }

            walletUser.setUserBalance(resultTopUpPaymentBalance); //Set the new balance

            walletUserRepository.save(walletUser); //Add balance user to wallet user.

            TopUpPaymentHistory response = new TopUpPaymentHistory();
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

    /**
     * Retrieves the payment histories of top-up payments for a user based on their account ID.
     *
     * @param accountId
     * @return A list top up payment histories
     * @throws IdNotFoundException
     */
    @Override
    public List<TopUpPaymentHistory> getHistoriesTopUpPaymentsUserByAccountIdUser(String accountId){
        List<TopUpPaymentHistory> histories = topUpPaymentHistoryRepository.findTopUpPaymentHistoriesByAccountId(accountId);
        if(histories == null){
            throw new IdNotFoundException("Account id user not found.");
        }
        return histories.isEmpty() ? List.of() : histories; //Check if histories of the accountId is empty then just return empty array.
    }

    @Override
    public TopUpPaymentHistory getHistoryTopUpPaymentUserByPaymentId(String paymentId){
        TopUpPaymentHistory history = topUpPaymentHistoryRepository.findTopUpPaymentHistoriesByPaymentId(paymentId);
        if(history == null){
            throw new IdNotFoundException("Payment id not found.");
        }

        return history;
    }

    @Override
    public Map<String, String> deleteHistoryPaymentById(UUID id) {
        Map<String, String> response = new HashMap<>();

        topUpPaymentHistoryRepository.findById(id).orElseThrow(() -> new IdNotFoundException("History id not found!"));
        topUpPaymentHistoryRepository.deleteById(id);

        response.put("id",id.toString());
        response.put("message","Success to delete history!");
        return response;
    }
}
