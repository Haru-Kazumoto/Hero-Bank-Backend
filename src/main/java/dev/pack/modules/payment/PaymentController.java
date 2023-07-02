package dev.pack.modules.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/top-up")
    public ResponseEntity<Payments.TopUpPaymentHistory> topUpBalance(@RequestBody @Valid Payments.TopUpPaymentRequest requestPayment) {
        Payments.TopUpPaymentHistory response = paymentService.topUpBalance(requestPayment);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
