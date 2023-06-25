package dev.pack.payload.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadResponse {

    private HttpStatus StatusCode;
    private Object Payload;

}
