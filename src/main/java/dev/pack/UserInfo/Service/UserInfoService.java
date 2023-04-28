package dev.pack.UserInfo.Service;

import dev.pack.Address.Model.AddressUser;
import dev.pack.User.Model.UserEntity;
import dev.pack.UserInfo.Model.UserInfo;
import dev.pack.UserInfo.Repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

//    public UserInfo createAddressUser(UserInfo userInfo){
//        UserEntity user = new UserEntity();
////        UserInfo.builder().userEntityId(user.getId());
//        return userInfoRepository.save(userInfo);
//    }

    public void findEmailUser(String email)throws DataIntegrityViolationException{
        Optional<UserInfo> emailUser = userInfoRepository.findByEmail(email);
        if(emailUser.isPresent()){
            throw new DataIntegrityViolationException(String.format("Email %s has already exists",email));
        }
    }

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
}
