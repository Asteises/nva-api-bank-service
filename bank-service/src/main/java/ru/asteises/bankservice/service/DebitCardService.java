package ru.asteises.bankservice.service;

import org.springframework.transaction.annotation.Transactional;
import ru.asteises.bankservice.model.dto.DebitCardVisualDto;
import ru.asteises.bankservice.model.dto.NewDebitCardDto;

import java.util.List;
import java.util.UUID;

public interface DebitCardService extends BankCardService {

    @Transactional
    DebitCardVisualDto addNewDebitCard(NewDebitCardDto newDebitCardDto);

    DebitCardVisualDto getDebitCardById(UUID cardId);

    @Transactional
    DebitCardVisualDto blockDebitCardById(UUID cardId);

    List<DebitCardVisualDto> getAllNonBlockedDebitCards();

    List<DebitCardVisualDto> getAllBlockedDebitCards();

}
