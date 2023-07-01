package dev.pack.modules.walletUser;

import dev.pack.modules.payment.Payments;
import dev.pack.payload.response.TransactionErrorResponse;
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

    private final WalletUserServiceImpl walletUserServiceImpl;
    private final ModelMapper modelMapper;

    @PostMapping(path = "addBalance")
    public ResponseEntity<?> addBalanceWallet(@RequestBody Payments.TopUpPaymentRequest requestPayment){
        Payments.TopUpPaymentReceipt body = modelMapper.map(requestPayment, Payments.TopUpPaymentReceipt.class);
        return null;
    }

    @GetMapping(path = "getWallet")
    public ResponseEntity<List<?>> getAllWalletUserData(){
        return ResponseEntity.ok(walletUserServiceImpl.getAllWalletUser());
    }

}
