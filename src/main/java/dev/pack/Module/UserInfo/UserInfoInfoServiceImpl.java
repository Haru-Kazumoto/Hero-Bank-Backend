package dev.pack.Module.UserInfo;

import dev.pack.Module.User.UserEntity;
import dev.pack.Utils.GenerateRandomNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserInfoInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final GenerateRandomNumber generator;

    public void findPhoneNumberUser(String phoneNumberUser)throws DataIntegrityViolationException{
        Optional<UserInfo> phoneNumber = userInfoRepository.findByPhoneNumber(phoneNumberUser);
        if(phoneNumber.isPresent()){
            throw new DataIntegrityViolationException("Phone number already in used!");
        }
    }

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
