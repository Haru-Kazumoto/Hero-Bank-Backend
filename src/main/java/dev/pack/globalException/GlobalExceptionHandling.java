package dev.pack.globalException;

import dev.pack.exception.IdNotFoundException;
import dev.pack.exception.MissMatchException;
import dev.pack.exception.UnsupportedPaymentPlatformException;
import dev.pack.payload.response.ErrorResponse;
import dev.pack.payload.response.ValidationErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissMatchException.class)
    public ResponseEntity<ErrorResponse> handleMissMatchException(MissMatchException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusResponse(HttpStatus.NOT_FOUND.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsupportedPaymentPlatformException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedPaymentPlatforms(UnsupportedPaymentPlatformException ex){
        ErrorResponse response = ErrorResponse.builder()
                .statusResponse(HttpStatus.BAD_REQUEST.name())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialError(BadCredentialsException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusResponse(HttpStatus.UNAUTHORIZED.name())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusResponse(status.toString())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleObjectNotFound(IdNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusResponse("NOT FOUND")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex
    ) {
        List<String> messages = new ArrayList<>();
        messages.add("There is duplicate value in unique field!");
        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(messages)
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex
                .getAllErrors()
                .forEach(
                        err -> errors.add(err.getDefaultMessage())
                );

        ValidationErrorResponse result = ValidationErrorResponse.builder()
                .statusCode(status.value())
                .message(errors)
                .build();

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.NOT_FOUND.value(),
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
        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, headers, status);
    }
}
