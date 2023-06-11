package dev.pack.Module.SavingsUser.Repository;

import dev.pack.Module.SavingsUser.Model.SavingsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SavingsUserRepository extends JpaRepository<SavingsUser, UUID> {
}
