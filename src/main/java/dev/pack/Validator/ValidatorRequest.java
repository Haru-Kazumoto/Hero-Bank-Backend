package dev.pack.Validator;

import dev.pack.Module.User.Model.UserEntity;
import dev.pack.Module.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidatorRequest {

    private final UserRepository userRepository;

    public void isEmailExists(String email) throws DataIntegrityViolationException {
        Optional<UserEntity> emailUser = userRepository.findByEmail(email);
        if(emailUser.isPresent()){
            throw new DataIntegrityViolationException(String.format("Email %s already exists",emailUser));
        }
    }
}
