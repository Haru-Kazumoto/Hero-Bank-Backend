package dev.pack.Module.WalletUser.Repository;

import dev.pack.Module.WalletUser.Model.WalletUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletUserRepository extends JpaRepository<WalletUser, UUID> {
}
