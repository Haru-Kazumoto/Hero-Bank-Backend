package dev.pack.UserInfo.Service;

import dev.pack.UserInfo.Repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public void findEmailUser(String email){
        userInfoRepository
                .findByEmail(email)
                .orElseThrow(() -> new DataIntegrityViolationException("Email has been used"));
    }
}
