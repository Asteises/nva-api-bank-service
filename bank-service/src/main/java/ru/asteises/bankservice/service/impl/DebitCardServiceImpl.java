package ru.asteises.bankservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.bankservice.exception.DebitCardNotFound;
import ru.asteises.bankservice.exception.NotEnoughFundsException;
import ru.asteises.bankservice.mapper.DebitCardMapper;
import ru.asteises.bankservice.model.dto.DebitCardBalanceInfoDto;
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

    @Override
    public DebitCardBalanceInfoDto showBankCardBalanceInfo(UUID cardId) {
        DebitCard debitCard = getDebitCardFromRepo(cardId);
        return DebitCardMapper.INSTANCE.entityToBalanceInfoDto(debitCard);
    }

    @Override
    public DebitCardBalanceInfoDto refundBankCard(UUID cardId, double refundSum) {
        DebitCard debitCard = getDebitCardFromRepo(cardId);
        debitCardRepo.save(refundBalance(debitCard, refundSum));
        log.info("Refund debit card success: {}", debitCard.getBalance());
        return DebitCardMapper.INSTANCE.entityToBalanceInfoDto(debitCard);
    }

    @Override
    public DebitCardBalanceInfoDto payFromBankCard(UUID cardId, double paySum) {
        DebitCard debitCard = getDebitCardFromRepo(cardId);
        // создаем переменную и передаем данные в метод оплаты
        Boolean isPaid = pay(debitCard, paySum);
        if (!isPaid) {
            throw new NotEnoughFundsException(debitCard.toString());
        } else {
            // обновляем данные в бд
            debitCardRepo.save(debitCard);
            return DebitCardMapper.INSTANCE.entityToBalanceInfoDto(debitCard);
        }
    }

    private DebitCard getDebitCardFromRepo(UUID cardId) throws DebitCardNotFound {
        DebitCard debitCard = debitCardRepo.getDebitCardById(cardId)
                .orElseThrow(() -> new DebitCardNotFound(cardId.toString()));
        log.info("Get debit card from repo: {}", debitCard.toString());
        return debitCard;
    }

    private DebitCard refundBalance(DebitCard debitCard, double refundSum) {
        log.info("income sum: {}", refundSum);
        double balance = debitCard.getBalance();
        debitCard.setBalance(balance + refundSum + bonus(refundSum));
        log.info("total balance: " + balance);
        return debitCard;
    }

    private double bonus(double refundSum) {
        double bonus = refundSum * 0.005 / 100;
        log.info("bonus: " + bonus);
        return bonus;
    }

    private Boolean pay(DebitCard debitCard, double paySum) {
        log.info("pay sum: " + paySum);
        double balance = debitCard.getBalance();
        // если баланс больше или равен сумме списания, то:
        if (balance >= paySum) {
            // вычитаем из баланса сумму платежа
            debitCard.setBalance(balance - paySum);
            log.info("new balance: " + balance);
            return true;
        } else {
            log.info("Not enough funds");
            return false;
        }
    }
}
