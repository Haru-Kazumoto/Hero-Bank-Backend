package dev.pack.modules.savingsUser;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/savings")
public class SavingsUserController {

    private final SavingsUserService savingsUserService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/create-savings")
    public ResponseEntity<SavingsUserResponse> createSavingsUser(@Valid @RequestBody SavingsUserRequest savingsUserRequest){
        SavingsUser mapToEntity = modelMapper.map(savingsUserRequest, SavingsUser.class);
        SavingsUser savedSavingsUser = savingsUserService.createSavingsUser(mapToEntity);
        SavingsUserResponse response = savingsUserService.convertToResponseDto(savedSavingsUser);

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping(path = "/update-savings/{id}")
    public ResponseEntity<SavingsUserResponse> updateSavingsById(
            @PathVariable("id") UUID id,
            @Valid @RequestBody SavingsUserUpdateRequest savingsUserDto
    ){
        SavingsUser mapToEntity = modelMapper.map(savingsUserDto, SavingsUser.class);
        SavingsUser savedSavingsUser = savingsUserService.updateSavingsUserById(mapToEntity, id);
        SavingsUserResponse response = savingsUserService.convertToResponseDto(savedSavingsUser);

        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping(path = "/delete-savings/{id}")
    public ResponseEntity<?> deleteSavingsById(@PathVariable("id")UUID id){
        return ResponseEntity.status(200).body(savingsUserService.deleteSavingsUserById(id));
    }

    @GetMapping(path = "/get-savings")
    public ResponseEntity<?> getAllSavingsUser(HttpServletResponse response){
        return ResponseEntity
                .status(response.getStatus())
                .body(savingsUserService.getAllRecord());
    }

    @GetMapping(path = "/get-savings/user-id/{userId}")
    public ResponseEntity<List<SavingsUser>> getSavingsUserByUserId(@PathVariable("userId") UUID userId){
        return ResponseEntity.status(200).body(savingsUserService.getSavingsUserByIdUser(userId));
    }
}
