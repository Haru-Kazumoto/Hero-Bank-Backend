package dev.pack.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            handleBadCredentialsException(request, response, (BadCredentialsException) authException);
        } else {
            handleDefaultException(request, response, authException);
        }
    }

    private void handleBadCredentialsException(HttpServletRequest request,
                                               HttpServletResponse response,
                                               BadCredentialsException ex) throws IOException {
        log.error("Bad credentials: {}", ex.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        UnauthorizedError result = UnauthorizedError.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .error("Unauthorized")
                .message("Invalid credentials")
                .path(request.getServletPath())
                .build();

        objectMapper.writeValue(response.getOutputStream(), result);
    }

    private void handleDefaultException(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException ex) throws IOException {
        log.error("Unauthorized error: {}", ex.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        UnauthorizedError result = UnauthorizedError.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .error("Unauthorized")
                .message(ex.getMessage())
                .path(request.getServletPath())
                .build();

        objectMapper.writeValue(response.getOutputStream(), result);
    }

    @Builder
    @Data
    static class UnauthorizedError {
        private int status;
        private String error;
        private String message;
        private String path;
    }
}
