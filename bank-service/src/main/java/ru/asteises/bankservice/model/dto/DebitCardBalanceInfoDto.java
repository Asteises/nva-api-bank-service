package ru.asteises.bankservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebitCardBalanceInfoDto {

    private double balance;
    private boolean isBlocked;
}
