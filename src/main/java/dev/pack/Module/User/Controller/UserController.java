package dev.pack.Module.User.Controller;

import dev.pack.Module.User.Dto.UserCreateDto;
import dev.pack.Module.User.Model.UserEntity;
import dev.pack.Response.MessageErrorResponse;
import dev.pack.Response.PayloadResponse;
import dev.pack.Module.User.Dto.UserCreateUpdateDto;
import dev.pack.Module.User.Service.Interfaces.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/user/")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping(path = "get-all")
    public ResponseEntity<?> getAllUser(){
        List<UserEntity> dataPayload = userService.getAllUser();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new PayloadResponse(
                                HttpStatus.OK,
                                dataPayload
                        )
                );
    }

    @PostMapping(path = "create")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserCreateDto user) throws DataIntegrityViolationException {
        try{
            UserEntity userEntity = mapper.map(user, UserEntity.class);
            UserEntity dataPayload = userService.createUserBody(userEntity);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            new PayloadResponse(
                                    HttpStatus.CREATED,
                                    dataPayload
                            )
                    );
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new MessageErrorResponse(
                            HttpStatus.CONFLICT,
                            List.of(ex.getMessage())
                    )
            );
        }
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<?> hardDeleteUserById(@PathVariable("id") UUID id) throws NullPointerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
        } catch (NullPointerException err) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageErrorResponse(
                            HttpStatus.CONFLICT,
                            List.of(err.getMessage())
                    )
            );
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<?> updateUserRecord(
            @PathVariable("id") UUID id,
            @RequestBody @Valid UserCreateUpdateDto userCreateUpdateDto,
            HttpServletResponse response) throws DataIntegrityViolationException{
        try {
            UserEntity user = mapper.map(userCreateUpdateDto, UserEntity.class);
            return ResponseEntity
                    .status(response.getStatus())
                    .body(
                            userService.updateUser(id, user)
                    );
        } catch (DataIntegrityViolationException err) {
            return ResponseEntity
                    .status(response.getStatus())
                    .body(
                            new MessageErrorResponse(
                                    HttpStatus.CONFLICT,
                                    List.of(err.getMessage())
                            )
                    );
        }
    }
}
