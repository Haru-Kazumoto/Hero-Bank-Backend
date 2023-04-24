package dev.pack.User.Controller;

import dev.pack.GlobalException.MessageResponse;
import dev.pack.User.Dto.UserDto;
import dev.pack.User.Model.UserEntity;
import dev.pack.User.Service.Interfaces.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user/")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    //TODO : CREATE CRUD METHOD FOR USER

    @GetMapping(path = "get-all")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity
                .ok()
                .body(userService.getAllUser());
    }

    @PostMapping(path = "create")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDto user) throws DataIntegrityViolationException {
        try{
            UserEntity userEntity = mapper.map(user, UserEntity.class);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.createUser(userEntity));
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new MessageResponse(
                            HttpStatus.CONFLICT,
                            List.of(ex.getMessage())
                    )
            );
        }
    }

}
