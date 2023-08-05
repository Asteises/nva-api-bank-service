package ru.asteises.bankservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.bankservice.exception.DebitCardNotFound;
import ru.asteises.bankservice.mapper.DebitCardMapper;
import ru.asteises.bankservice.model.dto.DebitCardVisualDto;
import ru.asteises.bankservice.model.dto.NewDebitCardDto;
import ru.asteises.bankservice.model.entity.DebitCard;
import ru.asteises.bankservice.repo.DebitCardRepo;
import ru.asteises.bankservice.service.DebitCardService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class DebitCardServiceImpl implements DebitCardService {

    private final DebitCardRepo debitCardRepo;

    @Override
    public DebitCardVisualDto addNewDebitCard(NewDebitCardDto newDebitCardDto) {
        DebitCard debitCard = DebitCardMapper.INSTANCE.dtoToEntity(newDebitCardDto);
        debitCardRepo.save(debitCard);
        log.info("New debit card create: {}", debitCard.toString());
        return DebitCardMapper.INSTANCE.entityToVisualDto(debitCard);
    }

    @Override
    public DebitCardVisualDto getDebitCardById(UUID cardId) {
        DebitCard debitCard = getDebitCardFromRepo(cardId);
        return DebitCardMapper.INSTANCE.entityToVisualDto(debitCard);
    }

    @Override
    public DebitCardVisualDto blockDebitCardById(UUID cardId) {
        DebitCard debitCard = getDebitCardFromRepo(cardId);
        debitCard.setBlocked(true);
        debitCardRepo.save(debitCard);
        log.info("Debit card {} is blocked {}", cardId, debitCard.isBlocked());
        return DebitCardMapper.INSTANCE.entityToVisualDto(debitCard);
    }

    @Override
    public List<DebitCardVisualDto> getAllNonBlockedDebitCards() {
        List<DebitCard> debitCards = debitCardRepo.getAllByBlockedFalse();
        return DebitCardMapper.INSTANCE.entityToVisualDto(debitCards);
    }

    @Override
    public List<DebitCardVisualDto> getAllBlockedDebitCards() {
        List<DebitCard> debitCards = debitCardRepo.getAllByBlockedTrue();
        return DebitCardMapper.INSTANCE.entityToVisualDto(debitCards);
    }

    private DebitCard getDebitCardFromRepo(UUID cardId) throws DebitCardNotFound {
        DebitCard debitCard = debitCardRepo.getDebitCardById(cardId)
                .orElseThrow(() -> new DebitCardNotFound(cardId.toString()));
        log.info("Get debit card from repo: {}", debitCard.toString());
        return debitCard;
    }
}
