package dev.pack.modules.userInfo;

import dev.pack.modules.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserInfoInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Override
    public UserInfo createUserInfoBody(UserEntity user) {
        UserInfo userInfo = user.getUserInfo();
        userInfo.setAccountNumber(
                generateAccountNumber()
        );
        userInfo.setUserEntityId(user);

        return userInfoRepository.save(userInfo);
    }

    public String generateAccountNumber() {
        Random random_number = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randomDigit = random_number.nextInt(9) + 1;
            builder.append(randomDigit);
        }

        return builder.toString();
    }
}
