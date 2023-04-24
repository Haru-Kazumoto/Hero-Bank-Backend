package dev.pack.Address.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddressUserDto {
    @NotEmpty(message = "Country must filled!")
    private String country;
    @NotEmpty(message = "Province must filled!")
    private String province;
    @NotEmpty(message = "City must filled!")
    private String city;
    @NotEmpty(message = "District must filled!")
    private String district;
}
