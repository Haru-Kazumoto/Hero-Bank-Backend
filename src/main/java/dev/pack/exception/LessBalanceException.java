package dev.pack.exception;

public class LessBalanceException extends RuntimeException{
    public LessBalanceException(String message) {
        super(message);
    }

    public LessBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
