package dev.pack.modules.walletUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletUserRepository extends JpaRepository<WalletUser, UUID> {
    Optional<WalletUser> findByWalletId(String walletID);
}
