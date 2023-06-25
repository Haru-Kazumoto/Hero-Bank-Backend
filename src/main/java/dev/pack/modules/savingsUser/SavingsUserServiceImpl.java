package dev.pack.modules.savingsUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingsUserServiceImpl implements SavingsUserService {

    private final SavingsUserRepository savingsUserRepository;

    @Override
    public List<SavingsUser> createSavingsUserBody(List<SavingsUser> savingsUser) {
        return savingsUserRepository.saveAll(savingsUser);
    }

    @Override
    public List<SavingsUser> getAllRecord() {
        return savingsUserRepository.findAll();
    }
}
