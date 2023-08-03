package ru.asteises.bankservice.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BankCard {

    private String cardNumber;
    private double balance;
    private boolean isBlocked;

}
