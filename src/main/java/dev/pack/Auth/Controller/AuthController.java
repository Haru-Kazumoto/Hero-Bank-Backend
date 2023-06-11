package dev.pack.Auth.Controller;

import dev.pack.Auth.AuthRequest;
import dev.pack.Auth.RegisterRequest;
import dev.pack.Auth.AuthResponse;
import dev.pack.Auth.Service.AuthService;
import dev.pack.Response.PayloadResponse;
import dev.pack.Module.User.Model.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    //TODO : THERE IS A BUG, JWT DIDN'T ACCESSED AS WELL (ALWAYS FORBIDDEN)

    private final AuthService authService;
    private final ModelMapper mapper;

    @PostMapping(path = "/register")
    public ResponseEntity<PayloadResponse> register(
            @RequestBody @Valid RegisterRequest registerRequest,
            BindingResult bindingResult,
            HttpServletResponse response) throws TransactionalException {
        //TODO : CREATE VALIDATION BINDING USING VALIDATOR.

        UserEntity user = mapper.map(registerRequest, UserEntity.class);

        return ResponseEntity
                .status(response.getStatus())
                .body(authService.register(user));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest authRequest,
            BindingResult bindingResult,
            HttpServletResponse response) throws TransactionalException{
        //TODO : CREATE VALIDATION BINDING USING VALIDATOR.
        return ResponseEntity
                .status(response.getStatus())
                .body(authService.authenticate(authRequest));
    }

    @GetMapping(path = "/hello")
    public String test(){
        return "Hello!";
    }
}