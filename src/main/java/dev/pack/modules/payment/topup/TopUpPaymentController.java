package dev.pack.modules.payment.topup;

import dev.pack.modules.payment.historyPayments.TopUpPaymentHistory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/payments")
public class TopUpPaymentController {

    private final TopUpPaymentService topUpPaymentService;

    @PostMapping("/top-up")
    public ResponseEntity<TopUpPaymentHistory> topUpBalance(@RequestBody @Valid TopUpPaymentsDto.Request requestPayment) {
        TopUpPaymentHistory response = topUpPaymentService.topUpBalance(requestPayment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/find-history-payments/account-id/{accountIdUser}")
    public ResponseEntity<List<TopUpPaymentHistory>> findTopUpPaymentHistoriesByAccountIdUser(
            @PathVariable("accountIdUser") String accountId
    ){
        List<TopUpPaymentHistory> findHistoriesByAccountIdUser = topUpPaymentService
                .getHistoriesTopUpPaymentsUserByAccountIdUser(accountId);
        return ResponseEntity.status(200).body(findHistoriesByAccountIdUser);
    }

    @GetMapping("/find-history-payments/payment-id/{paymentId}")
    public ResponseEntity<TopUpPaymentHistory> findTopUpPaymentHistoriesByPaymentId(
            @PathVariable("paymentId") String paymentId
    ){
        return ResponseEntity.status(200).body(topUpPaymentService.getHistoryTopUpPaymentUserByPaymentId(paymentId));
    }

    @DeleteMapping("/delete-history/{id}")
    public ResponseEntity<Map<String, String>> deleteHistoryById(@PathVariable("id")UUID id){
        return ResponseEntity.status(200).body(topUpPaymentService.deleteHistoryPaymentById(id));
    }
}
