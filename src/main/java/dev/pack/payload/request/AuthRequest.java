package dev.pack.payload.request;

import lombok.Data;

@Data
public class AuthRequest {

    private String pin;
    private String email;

}
