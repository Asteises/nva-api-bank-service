package ru.asteises.bankservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.bankservice.exception.CreditCardNotFound;
import ru.asteises.bankservice.mapper.CreditCardMapper;
import ru.asteises.bankservice.model.dto.CreditCardBalanceInfoDto;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCreditCardDto;
import ru.asteises.bankservice.model.entity.CreditCard;
import ru.asteises.bankservice.repo.CreditCardRepo;
import ru.asteises.bankservice.service.CreditCardService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepo creditCardRepo;

    @Override
    public CreditCardVisualDto addNewCreditCard(NewCreditCardDto newCreditCardDto) {
        CreditCard creditCard = CreditCardMapper.INSTANCE.dtoToEntity(newCreditCardDto);
        log.info("New credit card create: {}", creditCard.toString());
        creditCardRepo.save(creditCard);
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCard);
    }

    @Override
    public CreditCardVisualDto getCreditCardById(UUID cardId) {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCard);
    }

    @Override
    public CreditCardVisualDto changeCreditLimit(UUID cardId, double newCreditLimit) {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        creditCard.setCreditLimit(newCreditLimit);
        creditCardRepo.save(creditCard);
        log.info("Credit card {} change credit limit {}", creditCard, newCreditLimit);
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCard);
    }

    @Override
    public CreditCardVisualDto blockCreditCardById(UUID cardId) {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        creditCard.setBlocked(true);
        creditCardRepo.save(creditCard);
        log.info("Credit card {} is blocked {}", cardId, creditCard.isBlocked());
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCard);
    }

    @Override
    public List<CreditCardVisualDto> getAllNonBlockedCreditCards() {
        List<CreditCard> creditCards = creditCardRepo.getAllByBlockedFalse();
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCards);
    }

    @Override
    public List<CreditCardVisualDto> getAllBlockedCards() {
        List<CreditCard> creditCards = creditCardRepo.getAllByBlockedTrue();
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCards);
    }

    @Override
    public CreditCardBalanceInfoDto showCreditCardBalanceInfo(UUID cardId) {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        return CreditCardMapper.INSTANCE.entityToBalanceInfoDto(creditCard);
    }

    @Override
    public CreditCardBalanceInfoDto refundCreditCard(UUID cardId, double refundSum) {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        refundBalance(creditCard, refundSum);
        creditCardRepo.save(creditCard);
        log.info("Credit card balance refund {}", creditCard.toString());
        return CreditCardMapper.INSTANCE.entityToBalanceInfoDto(creditCard);
    }

    @Override
    public CreditCardBalanceInfoDto pay(UUID cardId, double paySum) {
        return null;
    }

    private CreditCard getCreditCardFromRepo(UUID cardId) throws CreditCardNotFound {
        CreditCard creditCard = creditCardRepo.getCreditCardById(cardId)
                .orElseThrow(() -> new CreditCardNotFound(cardId.toString()));
        log.info("Get credit card from repo: {}", creditCard.toString());
        return creditCard;
    }

    private void refundBalance(CreditCard creditCard, double refundSum) {
        log.info("refundSum: " + refundSum);
        double creditLimit = creditCard.getCreditLimit();
        double creditFunds = creditCard.getCreditFunds();
        double debitFunds = creditCard.getDebitFunds();
        // если есть кредитный долг, то вычисляем сумму долга
        if (creditLimit > creditFunds) {
            double debt = creditLimit - creditFunds;
            log.info("total debt: " + debt);
            // если входящий платеж равен или меньше долга, то просто зачисляем его в кредитные средства
            if (refundSum == debt || refundSum < debt) {
                creditCard.setCreditFunds(creditFunds + refundSum);
                log.info("credit funds после пополнения: " + creditFunds);
                // если входящий платеж больше долга, то закрываем долг и остаток зачисляем в собственные средства
            } else {
                creditCard.setCreditFunds(creditFunds + debt);
                creditCard.setDebitFunds(debitFunds + refundSum - debt);
                log.info("credit funds после пополнения: " + creditFunds);
                log.info("debit funds после пополнения: " + debitFunds);
            }
        } else {
            creditCard.setDebitFunds(debitFunds + refundSum);
            log.info("debit funds: " + debitFunds);
        }
    }
}
