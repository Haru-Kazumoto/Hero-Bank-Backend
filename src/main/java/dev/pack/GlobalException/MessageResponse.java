package dev.pack.GlobalException;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private HttpStatus statusCode;
    private List<String> message;

    public MessageResponse(int status, List<String> message) {
    }
}
