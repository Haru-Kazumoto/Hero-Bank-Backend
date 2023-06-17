package dev.pack.Module.SavingsUser;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
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
    public ResponseEntity<?> createSavingsUser(
            @RequestBody @Valid SavingsUserDto savingsUserDto,
            HttpServletResponse response
    ){
        try{
            List<SavingsUser> savingsUserList = Collections.singletonList(
                    modelMapper.map(savingsUserDto, SavingsUser.class)
            );

            return ResponseEntity
                    .status(response.getStatus())
                    .body(savingsUserService.createSavingsUserBody(savingsUserList));
        } catch (ErrorResponseException err){
            return ResponseEntity
                    .status(response.getStatus())
                    .body(err.getMessage());
        }
    }

    @GetMapping(path = "get-all")
    public ResponseEntity<?> getAllSavingsUser(HttpServletResponse response){
        return ResponseEntity
                .status(response.getStatus())
                .body(savingsUserService.getAllRecord());
    }
}
