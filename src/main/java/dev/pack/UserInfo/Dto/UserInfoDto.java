package dev.pack.UserInfo.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserInfoDto {

    @NotEmpty(message = "Nickname must filled!")
    private String username;

    @NotEmpty(message = "Phone number are required!")
    @Pattern(regexp = "^08\\d{12,14}$", message = "Phone number must be start with 08 !")
    @Min(value = 12, message = "Phone number must have minimal 12 letters!")
    @Max(value = 14, message = "Phone number only have maximal 14 letters!")
    private String phoneNumber;

}
