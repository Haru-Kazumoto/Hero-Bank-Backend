package dev.pack.payload.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageErrorResponse {

    private Integer statusCode;
    private List<String> message;


}