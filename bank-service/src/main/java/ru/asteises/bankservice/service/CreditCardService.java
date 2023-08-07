package ru.asteises.bankservice.service;

import org.springframework.transaction.annotation.Transactional;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCreditCardDto;

import java.util.List;
import java.util.UUID;

public interface CreditCardService extends BankCardService {

    @Transactional
    CreditCardVisualDto addNewCreditCard(NewCreditCardDto newCreditCardDto);

    CreditCardVisualDto getCreditCardById(UUID cardId);

    @Transactional
    CreditCardVisualDto changeCreditLimit(UUID cardId, double newCreditLimit);

    @Transactional
    CreditCardVisualDto blockCreditCardById(UUID cardId);

    List<CreditCardVisualDto> getAllNonBlockedCreditCards();

    List<CreditCardVisualDto> getAllBlockedCards();

}
