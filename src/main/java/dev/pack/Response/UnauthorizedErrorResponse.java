package dev.pack.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnauthorizedErrorResponse {
    private int statusCode;
    private String error;
    private String message;
}
