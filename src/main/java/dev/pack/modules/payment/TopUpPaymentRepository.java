package dev.pack.modules.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TopUpPaymentRepository extends JpaRepository<Payments.TopUpPaymentReceipt, UUID> {

}
