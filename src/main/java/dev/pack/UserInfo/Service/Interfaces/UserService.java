package dev.pack.UserInfo.Service.Interfaces;

import dev.pack.User.Model.UserEntity;
import dev.pack.UserInfo.Model.UserInfo;

public interface UserService {

    UserInfo createUserInfoBody(UserEntity user);

}
