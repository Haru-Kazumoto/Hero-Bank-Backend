package dev.pack.modules.savingsUser;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SavingsUserService {

    //Ada bug, pas create user id nya itu gak ke data.
    List<SavingsUser> createSavingsUsers(List<SavingsUser> savingsUsers); //Creating multiple body
    SavingsUser createSavingsUser(SavingsUser savingsUser);
    List<SavingsUser> getAllRecord();
    SavingsUser updateSavingsUserById(SavingsUser savingsUser, UUID savingsUserId);
    Map<String, String> deleteSavingsUserById(UUID savingUserId);
    SavingsUserResponse convertToResponseDto(SavingsUser savingsUser);
}
