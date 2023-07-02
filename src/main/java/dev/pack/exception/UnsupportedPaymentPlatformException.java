package dev.pack.exception;

public class UnsupportedPaymentPlatformException extends RuntimeException{
    public UnsupportedPaymentPlatformException(String message) {
        super(message);
    }
}
