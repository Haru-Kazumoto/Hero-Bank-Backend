package dev.pack.exception;

public class PaymentBalanceErrorException extends RuntimeException{
    public PaymentBalanceErrorException(String message) {
        super(message);
    }
}
