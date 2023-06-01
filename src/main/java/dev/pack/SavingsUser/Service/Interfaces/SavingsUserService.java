package dev.pack.SavingsUser.Service.Interfaces;

import dev.pack.SavingsUser.Model.SavingsUser;
import dev.pack.User.Model.UserEntity;

import java.util.List;

public interface SavingsUserService {

    List<SavingsUser> createSavingsUserBody(UserEntity user);

}
