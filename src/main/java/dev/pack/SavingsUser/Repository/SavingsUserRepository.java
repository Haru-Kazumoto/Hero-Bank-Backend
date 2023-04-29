package dev.pack.SavingsUser.Repository;

import dev.pack.SavingsUser.Model.SavingsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SavingsUserRepository extends JpaRepository<SavingsUser, UUID> {
}
