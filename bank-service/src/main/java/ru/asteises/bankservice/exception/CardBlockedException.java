package ru.asteises.bankservice.exception;

public class CardBlockedException extends RuntimeException {

    public CardBlockedException(String message) {
        super("Card is blocked: " + message);
    }
}
