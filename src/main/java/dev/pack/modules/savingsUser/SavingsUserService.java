package dev.pack.modules.savingsUser;

import java.util.List;

public interface SavingsUserService {

    //Ada bug, pas create user id nya itu gak ke data.
    List<SavingsUser> createSavingsUserBody(List<SavingsUser> savingsUser); //Creating single body

    List<SavingsUser> getAllRecord();
}
