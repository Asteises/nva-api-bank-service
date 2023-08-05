package ru.asteises.bankservice.exception;

public class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException(String message) {
        super("Not enough funds on your card: " + message);
    }
}
