package dev.pack.modules.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateUpdateDto {

    @NotEmpty(message = "Pin is required for updating data.")
    private String pin;

    @NotEmpty(message = "Email is required for updating data.")
    @Email(message = "Email pattern doesn't valid")
    private String email;
}
