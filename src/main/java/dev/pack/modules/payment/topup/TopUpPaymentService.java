package dev.pack.modules.payment.topup;

import dev.pack.modules.payment.paymentNotification.TopUpPaymentHistory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TopUpPaymentService {

    TopUpPaymentHistory topUpBalance(TopUpPaymentsDto.Request requestPayment);
    List<TopUpPaymentHistory> getHistoriesTopUpPaymentsUserByAccountIdUser(String accountId);
    TopUpPaymentHistory getHistoryTopUpPaymentUserByPaymentId(String paymentId);
    Map<String, String> deleteHistoryPaymentById(UUID id);
}
