package dev.pack.Module.SavingsUser.Service.Interfaces;

import dev.pack.Module.SavingsUser.Model.SavingsUser;
import dev.pack.Module.User.Model.UserEntity;

import java.util.List;

public interface SavingsUserService {

    List<SavingsUser> createSavingsUserBody(UserEntity user); //Creating body with user
    List<SavingsUser> createSavingsUserBody(List<SavingsUser> savingsUser); //Creating single body

    List<SavingsUser> getAllRecord();
}
