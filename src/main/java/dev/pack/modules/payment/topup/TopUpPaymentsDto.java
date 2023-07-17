package dev.pack.modules.payment.topup;

import dev.pack.enums.OutletPaymentsEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

public class TopUpPaymentsDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{ //Request top up payment
        @Min(value = 50_000L, message = "Minimum top up amount is Rp50.000,-")
        @Max(value = 2_000_000L, message = "Maximum top up amount is Rp2.000.000,-")
        private Long amount;

        @Enumerated(EnumType.STRING)
        private OutletPaymentsEnum outlet;

        private String accountId;
        private String walletId;
    }

    // Response //

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReceiptResponse{ //For user another bank send balance
        private String customerName;
        private String bank;
        private String idBank;
        private Long paymentAmount;
        private String idTransaction;
        private Date paymentDate;
    }
}
