package ru.asteises.bankservice.exception;

public class DebitCardNotFound extends RuntimeException {

    public DebitCardNotFound(String message) {
        super("Debit card not found: " + message);
    }
}
