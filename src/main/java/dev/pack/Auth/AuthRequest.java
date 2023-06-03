package dev.pack.Auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String pin;
    private String email;

}
