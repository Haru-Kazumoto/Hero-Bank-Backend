package dev.pack.modules.savingsUser;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface SavingsUserService {

    SavingsUser createSavingsUser(SavingsUser savingsUser);
    List<SavingsUser> getAllRecord();
    SavingsUser updateSavingsUserById(SavingsUser savingsUser, UUID savingsUserId);
    Map<String, String> deleteSavingsUserById(UUID savingUserId);
    SavingsUserResponse convertToResponseDto(SavingsUser savingsUser);
    List<SavingsUser> getSavingsUserByIdUser(UUID userId);
    List<SavingsUser> getSavingsUserByAccountIdUser(String accountId);
    SavingsUser addSavingsBalanceFromUserBalance(SavingsUser savingsUser);

}
