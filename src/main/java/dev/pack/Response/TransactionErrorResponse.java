package dev.pack.Response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionErrorResponse {

    private boolean transactionalStatus;
    private HttpStatus httpStatus;
    private String message;

}
