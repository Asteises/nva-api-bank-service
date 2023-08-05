package ru.asteises.bankservice.service;

import ru.asteises.bankservice.model.entity.BankCard;

import java.util.UUID;

public interface BankCardService {

    Object showBankCardBalanceInfo(UUID cardId);

    Object refundBankCard(UUID cardId, double refundSum);

    Object payFromBankCard(UUID cardId, double paySum);

    void checkCardBlockStatus(BankCard bankCard);
}
