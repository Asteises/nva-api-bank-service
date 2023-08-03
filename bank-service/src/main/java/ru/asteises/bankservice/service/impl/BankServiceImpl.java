package ru.asteises.bankservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCardDto;
import ru.asteises.bankservice.model.entity.CreditCard;
import ru.asteises.bankservice.repo.CreditCardRepo;
import ru.asteises.bankservice.service.BankService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {

    private final CreditCardRepo creditCardRepo;

    @Override
    public CreditCardVisualDto addNewCard(NewCardDto newCardDto) {
        CreditCard creditCard = new CreditCard();

        creditCard.setId(UUID.randomUUID());
        creditCard.setCardNumber(newCardDto.getCardNumber());
        creditCard.setCreditLimit(100000.0);
        creditCard.setCreditFunds(0.0);
        creditCard.setDebitFunds(0.0);
        creditCard.setBalance(creditCard.getDebitFunds() + creditCard.getCreditFunds());
        creditCard.setBlocked(newCardDto.isBlocked());
        creditCardRepo.save(creditCard);

        CreditCardVisualDto creditCardVisualDto = new CreditCardVisualDto();
        creditCardVisualDto.setId(creditCard.getId());
        return creditCardVisualDto;
    }
}
