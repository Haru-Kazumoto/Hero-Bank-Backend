package dev.pack.modules.auth;

import dev.pack.payload.request.AuthRequest;
import dev.pack.payload.request.RegisterRequest;
import dev.pack.payload.response.AuthResponse;
import dev.pack.payload.response.PayloadResponse;
import dev.pack.modules.user.UserEntity;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private final AuthService authService;
    private final ModelMapper mapper;

    @PostMapping(path = "/signUp")
    public ResponseEntity<PayloadResponse> register(@RequestBody @Valid RegisterRequest registerRequest) throws TransactionalException {

        UserEntity user = mapper.map(registerRequest, UserEntity.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(user));
    }

    @PostMapping(path = "/signIn")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) throws TransactionalException{
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(authService.authenticate(authRequest));
    }

    @GetMapping(path = "/hello")
    public String test(){
        return "Hello!";
    }
}