package dev.pack.modules.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.pack.enums.PaymentOutlets;
import dev.pack.modules.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

public class Payments {

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

        private String walletId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "history_topup_payment")
    @Entity
    public static class TopUpPaymentHistory{ //For result and history payment.
        @Id @GeneratedValue
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Exclude
        private UUID id;
        @Enumerated(EnumType.STRING)
        private PaymentOutlets outletName; //nih bug nih, tipe data nya db malah jadi smallint, harusnya enum
        private Long totalTopUpAmount;
        private UUID paymentId = UUID.randomUUID();

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
        private Date topUpDate = new Date();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userEntityId")
        private UserEntity userEntityId;

        @PrePersist
        public void setTopUpDate() {
            this.topUpDate = new Date();
        }
    }

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
