package dev.pack.WalletUser.Repository;

import dev.pack.WalletUser.Model.WalletUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletUserRepository extends JpaRepository<WalletUser, UUID> {
}
