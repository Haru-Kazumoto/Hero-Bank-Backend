package dev.pack.User.Repository;

import dev.pack.User.Model.UserEntity;
import dev.pack.UserInfo.Model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPinExists(Long pin);
}
