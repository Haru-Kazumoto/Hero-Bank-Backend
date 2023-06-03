package dev.pack.Auth.Service;

import dev.pack.Auth.AuthRequest;
import dev.pack.Auth.AuthResponse;
import dev.pack.Auth.RegisterRequest;
import dev.pack.Configuration.JWTService;
import dev.pack.Response.PayloadResponse;
import dev.pack.User.Model.UserEntity;
import dev.pack.User.Repository.UserRepository;
import dev.pack.User.Service.Interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public PayloadResponse register(UserEntity request){
        return new PayloadResponse(
                HttpStatus.CREATED,
                userService.createUserBody(request)
        );
    }

    public AuthResponse authenticate(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPin()
                )
        );
        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .message(String.format("Welcome back! %s",user.getUserInfo().getUsername()))
                .token(jwtToken)
                .build();
    }

}