package dev.pack.User.Dto;

import dev.pack.UserInfo.Dto.UserInfoDto;
import dev.pack.WalletUser.Dto.WalletUserDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotNull(message = "Pin must be filled!")
    @NotEmpty(message = "Pin cannot be empty!")
    private String pin;
    @NotNull(message = "Info user must be filled!")
    private UserInfoDto userInfo;
}
