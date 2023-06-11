package dev.pack.Module.UserInfo.Service.Interfaces;

import dev.pack.Module.User.Model.UserEntity;
import dev.pack.Module.UserInfo.Model.UserInfo;

public interface UserService {

    UserInfo createUserInfoBody(UserEntity user);

}
