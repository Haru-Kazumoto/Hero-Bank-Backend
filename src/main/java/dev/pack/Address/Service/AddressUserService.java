package dev.pack.Address.Service;

import dev.pack.Address.Model.AddressUser;
import dev.pack.UserInfo.Model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressUserService {

    public AddressUser createAddressUser(UserInfo userInfo){
        return AddressUser
                .builder()
                .id(userInfo.getAddressUser().getId())
                .country(userInfo.getAddressUser().getCountry())
                .province(userInfo.getAddressUser().getProvince())
                .city(userInfo.getAddressUser().getCity())
                .district(userInfo.getAddressUser().getDistrict())
                .userInfoId(userInfo)
                .build();
    }
}
