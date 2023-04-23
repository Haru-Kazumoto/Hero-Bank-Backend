package dev.pack.UserInfo.Repository;

import dev.pack.UserInfo.Model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, UUID> {
    Optional<UserInfo> findByEmail(String email);
}