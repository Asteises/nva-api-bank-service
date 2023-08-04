package ru.asteises.bankservice.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewDebitCardDto {

    @NonNull
    private String cardNumber;
    private double balance;
    private boolean isBlocked;
}
