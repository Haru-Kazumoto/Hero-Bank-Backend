package dev.pack.payload.request;

import dev.pack.modules.userInfo.UserInfo;
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
    @Size(
            min = 6,
            max = 8,
            message = "Pin must be between 6 and 8 number!"
    )
    @Pattern(
            regexp = "\\d+", //Only receive digits
            message = "Pin must contain only digits"
    )
    private String pin;

    @Email(message = "Email pattern doesn't valid")
    @NotEmpty(message = "Email is required!")
    private String email;

    @NotNull(message = "User info is required!")
    private UserInfo userInfo;

}
