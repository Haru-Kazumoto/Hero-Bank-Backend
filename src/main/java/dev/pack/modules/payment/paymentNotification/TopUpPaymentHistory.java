package dev.pack.modules.payment.paymentNotification;


import com.fasterxml.jackson.annotation.JsonFormat;
import dev.pack.enums.OutletPaymentsEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history_topup_payment_table")
@Entity
public class TopUpPaymentHistory{ //For result and history payment.

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OutletPaymentsEnum outletName;
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
