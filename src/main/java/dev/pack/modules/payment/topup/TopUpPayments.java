package dev.pack.modules.payment.topup;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.pack.enums.PaymentOutlets;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

public class TopUpPayments {

    //Request//

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopUpPaymentRequest{ //Request top up payment
        @Min(value = 50_000L, message = "Minimum top up amount is Rp50.000,-")
        @Max(value = 2_000_000L, message = "Maximum top up amount is Rp2.000.000,-")
        private Long amount;

        @Enumerated(EnumType.STRING)
        private PaymentOutlets outlet;
        private String accountId;
        private String walletId;
    }


    // Model //

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "history_topup_payment")
    @Entity
    public static class TopUpPaymentHistory{ //For result and history payment.

        @Id @GeneratedValue
        private UUID id;

        @Enumerated(EnumType.STRING)
        private PaymentOutlets outletName;
        private Long totalTopUpAmount;
        private String paymentId;

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        private Date topUpDate = new Date();

        private String accountId; //ambil akun id dari si user.
        private String walletId; //Ambil wallet id nya si user, jadi accountId sama walletId harus sama dari body user dan relasi nya (walletUser)

        @PrePersist
        public void setTopUpDate() {
            this.topUpDate = new Date();
        }
    }

    // Response //

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentReceiptResponse{ //For user another bank send balance
        private String customerName;
        private String bank;
        private String idBank;
        private Long paymentAmount;
        private String idTransaction;
        private Date paymentDate;
    }
}
