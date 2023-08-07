package ru.asteises.bankservice.service;

import org.springframework.transaction.annotation.Transactional;
import ru.asteises.bankservice.model.entity.BankCard;

import java.util.UUID;

public interface BankCardService {

    Object showBankCardBalanceInfo(UUID cardId);

    @Transactional
    Object refundBankCard(UUID cardId, double refundSum);

    @Transactional
    Object payFromBankCard(UUID cardId, double paySum);

    void checkCardBlockStatus(BankCard bankCard);
}
