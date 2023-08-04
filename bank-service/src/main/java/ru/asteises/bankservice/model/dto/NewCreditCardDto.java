package ru.asteises.bankservice.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCreditCardDto {

    @NonNull
    private String cardNumber;
    @NonNull
    private Double creditLimit;
    @NonNull
    private Double creditFunds;
    private boolean isBlocked;
}
