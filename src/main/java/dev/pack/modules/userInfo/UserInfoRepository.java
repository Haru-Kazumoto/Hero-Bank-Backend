package dev.pack.modules.userInfo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, UUID> {
    Optional<UserInfo> findByPhoneNumber(String phoneNumber);
    UserInfo findUserInfoByIdCardNumber(String idCardNumber);
}