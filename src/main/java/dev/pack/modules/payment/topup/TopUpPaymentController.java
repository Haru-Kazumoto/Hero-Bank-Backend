package dev.pack.modules.payment.topup;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/payments")
public class TopUpPaymentController {

    private final TopUpPaymentService topUpPaymentService;

    @PostMapping("/top-up")
    public ResponseEntity<TopUpPayments.TopUpPaymentHistory> topUpBalance(@RequestBody @Valid TopUpPayments.TopUpPaymentRequest requestPayment) {
        TopUpPayments.TopUpPaymentHistory response = topUpPaymentService.topUpBalance(requestPayment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/find-history-payments/{accountIdUser}")
    public ResponseEntity<List<TopUpPayments.TopUpPaymentHistory>> findTopUpPaymentHistoriesByAccountIdUser(
            @PathVariable("accountIdUser") String accountId
    ){
        List<TopUpPayments.TopUpPaymentHistory> findHistoriesByAccountIdUser = topUpPaymentService
                .findHistoriesTopUpPaymentsUserByAccountIdUser(accountId);
        return ResponseEntity.status(200).body(findHistoriesByAccountIdUser);
    }

}
