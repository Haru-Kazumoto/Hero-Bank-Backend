package dev.pack.modules.payment.topup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopUpPaymentHistoryRepository extends JpaRepository<TopUpPayments.TopUpPaymentHistory, UUID> {
    TopUpPayments.TopUpPaymentHistory findTopUpPaymentHistoriesByPaymentId(String paymentId);

    @Query(
            value = "SELECT * FROM history_topup_payment t WHERE t.account_id = :accountId",
            nativeQuery = true
    )
    List<TopUpPayments.TopUpPaymentHistory> findTopUpPaymentHistoriesByAccountId(@Param("accountId") String accountId);
}
