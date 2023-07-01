package dev.pack.modules.payment;

import dev.pack.modules.walletUser.WalletUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public ResponseEntity<Payments.TopUpPaymentReceipt> topUpBalance(@RequestBody @Valid Payments.TopUpPaymentRequest requestPayment) {
        Payments.TopUpPaymentReceipt response = paymentService.topUpBalance(requestPayment); //ADA BUG MODEL MAPPER COK BANGSAT BAT MODEL MAPPER ANJGGG
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
