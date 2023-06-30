package dev.pack.modules.payment;

import dev.pack.enums.PaymentOutlets;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

public class Payments {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopUpPaymentRequest{
        @Min(value = 50_000L, message = "Minimum top up amount is Rp50.000,-")
        @Max(value = 2_000_000L, message = "Maximum top up amount is Rp2.000.000,-")
        @NotEmpty(message = "Amount top up value is required!")
        private Long amount;

        @Enumerated(EnumType.STRING)
        private PaymentOutlets outlet;

        private Long walletId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentReceiptResponse{
        private String customersName;
        private String bank;
        private String idBank;
        private Long paymentAmount;
        private String idTransaction;
        private Date paymentDate;
    }

}
