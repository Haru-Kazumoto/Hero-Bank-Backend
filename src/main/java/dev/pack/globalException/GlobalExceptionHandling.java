package dev.pack.globalException;

import dev.pack.payload.response.MessageErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex
    ) {
        List<String> messages = new ArrayList<>();
        messages.add("There is duplicate value in unique field!");
        MessageErrorResponse errorResponse = MessageErrorResponse.builder()
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

        MessageErrorResponse result = MessageErrorResponse.builder()
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
        MessageErrorResponse response = new MessageErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, headers, status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        MessageErrorResponse response = new MessageErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(response, headers, status);
    }
}
