package dev.pack.User.Validator;

import dev.pack.User.Model.UserEntity;
import dev.pack.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void isEmailExists(String email) throws DataIntegrityViolationException {
        Optional<UserEntity> emailUser = userRepository.findByEmail(email);
        if(emailUser.isPresent()){
            throw new DataIntegrityViolationException(String.format("Email %s already exists",emailUser));
        }
    }
}
