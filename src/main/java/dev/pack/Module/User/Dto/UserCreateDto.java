package dev.pack.Module.User.Dto;

import dev.pack.Module.UserInfo.Dto.UserInfoDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateDto {

    @NotNull(message = "Pin must be filled!")
    @NotEmpty(message = "Pin cannot be empty!")
    private String pin;

    @Email(message = "Email pattern doesn't valid!")
    @NotEmpty(message = "Email user must be filled!")
    private String email;

    @NotNull(message = "Info user must be filled!")
    private UserInfoDto userInfo;

}
