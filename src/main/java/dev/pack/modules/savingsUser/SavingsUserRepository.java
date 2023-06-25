package dev.pack.modules.savingsUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SavingsUserRepository extends JpaRepository<SavingsUser, UUID> {
}
