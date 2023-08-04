package ru.asteises.bankservice.exception;

public class CreditCardNotFound extends RuntimeException {

    public CreditCardNotFound(String message) {
        super("Credit card not found: " + message);
    }
}
