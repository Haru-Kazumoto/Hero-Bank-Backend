package dev.pack.modules.payment.paymentNotification;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "cash_withdrawal_history_table")
public class CashWithdrawalHistory {

    @Id @GeneratedValue
    private UUID id;

}
