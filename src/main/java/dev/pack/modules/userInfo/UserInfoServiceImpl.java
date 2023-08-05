package dev.pack.modules.userInfo;

import dev.pack.modules.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserInfo createUserInfoBody(UserEntity user) {
        UserInfo userInfo = user.getUserInfo();
        userInfo.setUserEntityId(user);

        return userInfoRepository.save(userInfo);
    }
}
