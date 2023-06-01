package dev.pack.UserInfo.Service.Implements;

import dev.pack.User.Model.UserEntity;
import dev.pack.UserInfo.Model.UserInfo;
import dev.pack.UserInfo.Repository.UserInfoRepository;
import dev.pack.UserInfo.Service.Interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;

    public void findPhoneNumberUser(String phoneNumberUser)throws DataIntegrityViolationException{
        Optional<UserInfo> phoneNumber = userInfoRepository.findByPhoneNumber(phoneNumberUser);
        if(phoneNumber.isPresent()){
            throw new DataIntegrityViolationException("Phone number already in used!");
        }
    }

    public String generateNumberForAccountNumber() {
        Random random_number = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randomDigit = random_number.nextInt(9) + 1;
            builder.append(randomDigit);
        }

        return builder.toString();
    }

    @Override
    public UserInfo createUserInfoBody(UserEntity user) {
        UserInfo userInfo = user.getUserInfo();
        userInfo.setAccountNumber(
                generateNumberForAccountNumber()
        );
        userInfo.setUserEntityId(user);

        return userInfoRepository.save(userInfo);
    }
}
