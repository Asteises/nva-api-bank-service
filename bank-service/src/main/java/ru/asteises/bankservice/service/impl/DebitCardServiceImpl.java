package ru.asteises.bankservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.asteises.bankservice.model.dto.DebitCardVisualDto;
import ru.asteises.bankservice.model.dto.NewDebitCardDto;
import ru.asteises.bankservice.repo.DebitCardRepo;
import ru.asteises.bankservice.service.DebitCardService;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DebitCardServiceImpl implements DebitCardService {

    private DebitCardRepo debitCardRepo;

    @Override
    public DebitCardVisualDto addNewDebitCard(NewDebitCardDto newDebitCardDto) {
        return null;
    }

    @Override
    public DebitCardVisualDto getDebitCardById(UUID cardId) {
        return null;
    }

    @Override
    public DebitCardVisualDto blockDebitCardById(UUID cardId) {
        return null;
    }

    @Override
    public List<DebitCardVisualDto> getAllNonBlockedDebitCards() {
        return null;
    }

    @Override
    public List<DebitCardVisualDto> getAllBlockedDebitCards() {
        return null;
    }
}
