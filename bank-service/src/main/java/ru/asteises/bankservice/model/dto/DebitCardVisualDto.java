package ru.asteises.bankservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DebitCardVisualDto {

    private UUID id;
    private String cardNumber;
    private double balance;
    private boolean isBlocked;
}
