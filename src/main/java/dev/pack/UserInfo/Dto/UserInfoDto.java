package dev.pack.UserInfo.Dto;

import dev.pack.Address.Dto.AddressUserDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserInfoDto {

    @NotEmpty(message = "Email must filled!")
    @Email(message = "Email pattern doesn't valid!")
    private String email;
    @NotEmpty(message = "Official Name must filled!")
    private String officialName;
    @NotEmpty(message = "Nickname must filled!")
    private String nickname;
    @NotNull(message = "Address user cannot be null!")
    private AddressUserDto addressUser;

}
