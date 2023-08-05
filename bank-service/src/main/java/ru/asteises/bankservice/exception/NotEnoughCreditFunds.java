package ru.asteises.bankservice.exception;

public class NotEnoughCreditFunds extends RuntimeException {
    public NotEnoughCreditFunds(String message) {
        super("Not enough credit funds on your card: " + message);
    }
}
