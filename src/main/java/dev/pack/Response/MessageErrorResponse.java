package dev.pack.Response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageErrorResponse {

    private HttpStatus statusCode;
    private List<String> message;

}
