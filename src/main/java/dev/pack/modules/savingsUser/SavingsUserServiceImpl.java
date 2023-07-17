package dev.pack.modules.savingsUser;

import dev.pack.exception.IdNotFoundException;
import dev.pack.modules.user.UserEntity;
import dev.pack.modules.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SavingsUserServiceImpl implements SavingsUserService {

    private final SavingsUserRepository savingsUserRepository;
    private final UserRepository userRepository;

    @Override
    public SavingsUser createSavingsUser(SavingsUser savingsUser) {
        UserEntity userId = userRepository
                .findById(savingsUser.getUserId())
                .orElseThrow(() -> new IdNotFoundException(String.format("Id user [%s] doesn't exists!",savingsUser.getUserId())));

        savingsUser.setUserId(savingsUser.getUserId());
        savingsUser.setUserEntityId(userId);
        return savingsUserRepository.save(savingsUser);
    }

    @Override
    public List<SavingsUser> getAllRecord() {
        return savingsUserRepository.findAll();
    }

    @Override
    public List<SavingsUser> getSavingsUserByIdUser(UUID userId) {
        List<SavingsUser> id_user = savingsUserRepository.findSavingsUserByUserId(userId);
        if(id_user == null){
            throw new IdNotFoundException("User id is not found.");
        }

        return id_user.isEmpty() ? List.of() : id_user;
    }

    @Override
    public SavingsUser updateSavingsUserById(SavingsUser savingsUser, UUID savingsUserId) {
        SavingsUser savingsData = savingsUserRepository
                .findById(savingsUserId)
                .orElseThrow(() -> new IdNotFoundException(String.format("Id user [%s] not found.", savingsUserId)));

        savingsData.setTitle(savingsUser.getTitle());
        savingsData.setCollectedPlans(savingsUser.getCollectedPlans());

        return savingsUserRepository.save(savingsData);
    }

    @Override
    public Map<String, String> deleteSavingsUserById(UUID savingUserId) {
        Map<String, String> response = new HashMap<>();

        savingsUserRepository
                .findById(savingUserId)
                .orElseThrow(() -> new IdNotFoundException("Id user not found."));

        savingsUserRepository.deleteById(savingUserId);

        response.put("status", HttpStatus.OK.toString());
        response.put("message",String.format("Id [%s] success to delete!", savingUserId));

        return response;
    }

    @Override
    public SavingsUser addSavingsBalanceFromUserBalance(SavingsUser savingsUser) {

        return null;
    }

    @Override
    public SavingsUserResponse convertToResponseDto(SavingsUser savingsUser) {
        SavingsUserResponse responseDto = new SavingsUserResponse();
        responseDto.setId(savingsUser.getId());
        responseDto.setTitle(savingsUser.getTitle());
        responseDto.setCollectedPlans(savingsUser.getCollectedPlans());
        responseDto.setSavingsBalance(savingsUser.getSavingsBalance());
        return responseDto;
    }
}
