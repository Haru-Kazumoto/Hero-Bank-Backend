package dev.pack.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findById(@NonNull UUID id);
    Optional<UserEntity> findByPin(String pin);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByAccountId(String accountId);
}