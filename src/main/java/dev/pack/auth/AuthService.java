package dev.pack.auth;

import dev.pack.configuration.JWTService;
import dev.pack.payload.request.AuthRequest;
import dev.pack.payload.response.AuthResponse;
import dev.pack.payload.response.PayloadResponse;
import dev.pack.modules.user.UserEntity;
import dev.pack.modules.user.UserRepository;
import dev.pack.modules.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
