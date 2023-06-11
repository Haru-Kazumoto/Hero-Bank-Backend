package dev.pack.GlobalException;

import dev.pack.Response.MessageErrorResponse;
import dev.pack.Response.TransactionErrorResponse;
import dev.pack.Response.UnauthorizedErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialException(
            BadCredentialsException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        var response = new UnauthorizedErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                String.format("Invalid credential : %s", ex.getCause())
        );

        return new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(
            AuthenticationException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){
        UnauthorizedErrorResponse response = new UnauthorizedErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Missing or malformed jwt"
        );

        return new ResponseEntity<>(response, headers, status);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<TransactionErrorResponse> handleTransactionError(TransactionException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new TransactionErrorResponse(
                    false,
                    HttpStatus.NOT_ACCEPTABLE,
                    ex.getMessage()
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());
        MessageErrorResponse errorResponse = new MessageErrorResponse(HttpStatus.CONFLICT, messages);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        MessageErrorResponse response = new MessageErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                Collections.singletonList(ex.getMessage())
        );

        return new ResponseEntity<>(response, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        MessageErrorResponse response = new MessageErrorResponse(
                HttpStatus.NOT_FOUND,
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        MessageErrorResponse response = new MessageErrorResponse(
                HttpStatus.NOT_ACCEPTABLE,
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        MessageErrorResponse response = new MessageErrorResponse(
                HttpStatus.NOT_FOUND,
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, headers, status);
    }
}
