package dev.pack.modules.user;

import dev.pack.modules.userInfo.UserInfoService;
import dev.pack.payload.response.ValidationErrorResponse;
import dev.pack.payload.response.PayloadResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping(path = "/get-all")
    public ResponseEntity<?> getAllUsers() {
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

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> hardDeleteUserById(@PathVariable("id") UUID id) throws NullPointerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
        } catch (NoSuchElementException err) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ValidationErrorResponse(
                            HttpStatus.CONFLICT.value(),
                            List.of(err.getMessage())
                    )
            );
        }
    }

    @PutMapping(path = "/update/{id}")
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
                            new ValidationErrorResponse(
                                    HttpStatus.CONFLICT.value(),
                                    List.of(err.getMessage())
                            )
                    );
        }
    }
}
