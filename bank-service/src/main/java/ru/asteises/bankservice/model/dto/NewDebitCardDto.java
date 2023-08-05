package ru.asteises.bankservice.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewDebitCardDto {

    @NonNull
    @Min(value = 16)
    @Max(value = 16)
    private String cardNumber;
    private double balance;
    private boolean isBlocked;
}
