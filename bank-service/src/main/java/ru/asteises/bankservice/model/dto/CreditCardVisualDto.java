package ru.asteises.bankservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreditCardVisualDto {

    private UUID id;
    private String cardNumber;
    private double creditLimit;
    private double creditFunds;
    private double debitFunds;
    private boolean isBlocked;
}
