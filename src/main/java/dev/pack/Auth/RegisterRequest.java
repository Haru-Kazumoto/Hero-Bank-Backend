package dev.pack.Auth;

import dev.pack.Module.UserInfo.UserInfo;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Pin is required!")
    @Max(value = 6, message = "Pin cannot great than 6 number!")
    @Min(value = 6, message = "Pin cannot less than 6 number")
    private String pin;

    @Email(message = "Email pattern doesn't valid")
    @NotEmpty(message = "Email is required!")
    private String email;

    @NotNull(message = "User info is required!")
    private UserInfo userInfo;

}
