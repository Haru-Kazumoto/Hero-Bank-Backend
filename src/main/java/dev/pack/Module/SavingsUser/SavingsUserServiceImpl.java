package dev.pack.Module.SavingsUser;

import dev.pack.Module.User.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingsUserServiceImpl implements SavingsUserService {

    private final SavingsUserRepository savingsUserRepository;

    @Override
    public List<SavingsUser> createSavingsUserBody(UserEntity user) {
        List<SavingsUser> savingsUserList = user.getSavingsUsers();

        for (SavingsUser savingsUser : savingsUserList) {
            savingsUser.setUserEntityId(user);
        }

        return savingsUserRepository.saveAll(savingsUserList);
    }

    @Override
    public List<SavingsUser> createSavingsUserBody(List<SavingsUser> savingsUser) {
        return savingsUserRepository.saveAll(savingsUser);
    }

    @Override
    public List<SavingsUser> getAllRecord() {
        return savingsUserRepository.findAll();
    }
}
