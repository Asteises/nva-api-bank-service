package ru.asteises.bankservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardBalanceInfoDto {

    private double cardNumber;
    private double creditLimit;
    private double creditFunds;
    private double debitFunds;
    private double cashback;
}
