package ru.asteises.bankservice.service;

import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCardDto;

public interface BankService {

    CreditCardVisualDto addNewCard(NewCardDto newCardDto);
}
