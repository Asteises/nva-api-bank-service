package ru.asteises.bankservice.service;

import ru.asteises.bankservice.model.dto.DebitCardVisualDto;
import ru.asteises.bankservice.model.dto.NewDebitCardDto;

import java.util.List;
import java.util.UUID;

public interface DebitCardService extends BankCardService {

    DebitCardVisualDto addNewDebitCard(NewDebitCardDto newDebitCardDto);

    DebitCardVisualDto getDebitCardById(UUID cardId);

    DebitCardVisualDto blockDebitCardById(UUID cardId);

    List<DebitCardVisualDto> getAllNonBlockedDebitCards();

    List<DebitCardVisualDto> getAllBlockedDebitCards();

}
