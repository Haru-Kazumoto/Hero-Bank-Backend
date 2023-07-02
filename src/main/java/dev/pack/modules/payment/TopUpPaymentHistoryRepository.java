package dev.pack.modules.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TopUpPaymentHistoryRepository extends JpaRepository<Payments.TopUpPaymentHistory, UUID> {
    Payments.TopUpPaymentHistory findByPaymentId(UUID paymentId);

    //Find history payment by range date
//    @Query("SELECT t FROM TopUpPaymentReceipt t WHERE t.topUpDate BETWEEN :startDate AND :endDate") //STATUS:Not tested yet.
//    List<Payments.TopUpPaymentReceipt> findByTopUpDateRange(Date startDate, Date endDate);
}
