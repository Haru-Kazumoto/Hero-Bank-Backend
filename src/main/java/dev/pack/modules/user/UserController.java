package dev.pack.modules.user;

import dev.pack.modules.payment.topup.TopUpPayments;
import dev.pack.payload.response.ValidationErrorResponse;
import dev.pack.payload.response.PayloadResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping(path = "get-all")
    public ResponseEntity<?> getAllUser() {
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

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<?> hardDeleteUserById(@PathVariable("id") UUID id) throws NullPointerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUserById(id));
        } catch (NullPointerException err) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ValidationErrorResponse(
                            HttpStatus.CONFLICT.value(),
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
                            new ValidationErrorResponse(
                                    HttpStatus.CONFLICT.value(),
                                    List.of(err.getMessage())
                            )
                    );
        }
    }
}
