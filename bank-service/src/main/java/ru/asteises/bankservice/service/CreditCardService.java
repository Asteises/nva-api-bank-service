package ru.asteises.bankservice.service;

import ru.asteises.bankservice.model.dto.CreditCardBalanceInfoDto;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCreditCardDto;

import java.util.List;
import java.util.UUID;

public interface CreditCardService {

    CreditCardVisualDto addNewCreditCard(NewCreditCardDto newCreditCardDto);
    CreditCardVisualDto getCreditCardById(UUID cardId);
    CreditCardVisualDto changeCreditLimit(UUID cardId, double newCreditLimit);
    CreditCardVisualDto blockCreditCardById(UUID cardId);
    List<CreditCardVisualDto> getAllNonBlockedCreditCards();
    List<CreditCardVisualDto> getAllBlockedCards();

    CreditCardBalanceInfoDto showCreditCardBalanceInfo(UUID cardId);
    CreditCardBalanceInfoDto refundCreditCard(UUID cardId, double refundSum);
    CreditCardBalanceInfoDto pay(UUID cardId, double paySum);

}
