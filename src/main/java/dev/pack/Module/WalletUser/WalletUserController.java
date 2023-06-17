package dev.pack.Module.WalletUser;

import dev.pack.Response.TransactionErrorResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/walletUser/")
public class WalletUserController {

    private final WalletUserService walletUserService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "addBalance")
    public ResponseEntity<?> addBalanceWallet(
            @RequestBody WalletUserDto walletUserDto,
             BigInteger amountBalance
    ){
        try{
            WalletUser walletUser = modelMapper.map(walletUserDto, WalletUser.class);

            walletUserService.addBalanceForWalletUser(amountBalance, walletUser);

            return ResponseEntity
                    .accepted()
                    .body(new TransactionErrorResponse(
                            true,
                            HttpStatus.ACCEPTED,
                            "Adding balance to wallet is success!"
                    ));
        }catch (Exception exception){
            return ResponseEntity
                    .badRequest()
                    .body(new TransactionErrorResponse(
                            false,
                            HttpStatus.BAD_REQUEST,
                            exception.getLocalizedMessage()
                    ));
        }
    }

    @GetMapping(path = "getWallet")
    public ResponseEntity<List<?>> getAllWalletUserData(){
        return ResponseEntity.ok(walletUserService.getWalletUserData());
    }

}
