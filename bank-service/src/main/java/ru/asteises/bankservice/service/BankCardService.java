package ru.asteises.bankservice.service;

import java.util.UUID;

public interface BankCardService {

    Object showBankCardBalanceInfo(UUID cardId);

    Object refundBankCard(UUID cardId, double refundSum);

    Object payFromBankCard(UUID cardId, double paySum);
}
