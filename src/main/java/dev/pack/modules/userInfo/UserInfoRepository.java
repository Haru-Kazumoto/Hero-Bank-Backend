package dev.pack.modules.userInfo;

import dev.pack.modules.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, UUID> {
    UserInfo findUserInfoByIdCardNumber(String idCardNumber);
}