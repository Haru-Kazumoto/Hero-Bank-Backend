package dev.pack.Address.Service;

import dev.pack.Address.Model.AddressUser;
import dev.pack.Address.Repository.AddressRepository;
import dev.pack.UserInfo.Model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressUserService {

    private final AddressRepository addressRepository;

    public AddressUser createAddressUser(AddressUser addressUser){
        UserInfo userInfo = new UserInfo();
        addressUser.setUserInfoId(userInfo);

        return addressRepository.save(addressUser);
    }

}
