package dev.pack.Module.SavingsUser;

import dev.pack.Module.User.UserEntity;

import java.util.List;

public interface SavingsUserService {

    List<SavingsUser> createSavingsUserBody(List<SavingsUser> savingsUser); //Creating single body

    List<SavingsUser> getAllRecord();
}
