package dev.pack.modules.savingsUser;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/savings")
public class SavingsUserController {

    private final SavingsUserService savingsUserService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "create-savings")
    public ResponseEntity<?> createSavingsUser(@RequestBody @Valid SavingsUserDto savingsUserDto){
            List<SavingsUser> savingsUserList = Collections.singletonList(
                    modelMapper.map(savingsUserDto, SavingsUser.class)
            );

            return ResponseEntity
                    .status(201)
                    .body(savingsUserService.createSavingsUserBody(savingsUserList));
    }

    @GetMapping(path = "get-all")
    public ResponseEntity<?> getAllSavingsUser(HttpServletResponse response){
        return ResponseEntity
                .status(response.getStatus())
                .body(savingsUserService.getAllRecord());
    }
}
