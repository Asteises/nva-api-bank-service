package ru.asteises.bankservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.bankservice.exception.CreditCardNotFound;
import ru.asteises.bankservice.exception.NotEnoughCreditFunds;
import ru.asteises.bankservice.exception.NotEnoughFundsException;
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
        if (creditCard.getCreditFunds() == creditCard.getCreditLimit()) {
            creditCard.setCreditLimit(newCreditLimit);
            creditCard.setCreditFunds(newCreditLimit);
        } else {
            throw new NotEnoughCreditFunds(String.valueOf(creditCard.getCreditFunds()));
        }
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
        List<CreditCard> creditCards = creditCardRepo.getAllByBlockedFalse(false);
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCards);
    }

    @Override
    public List<CreditCardVisualDto> getAllBlockedCards() {
        List<CreditCard> creditCards = creditCardRepo.getAllByBlockedTrue(true);
        return CreditCardMapper.INSTANCE.entityToVisualDto(creditCards);
    }

    @Override
    public CreditCardBalanceInfoDto showBankCardBalanceInfo(UUID cardId) {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        return CreditCardMapper.INSTANCE.entityToBalanceInfoDto(creditCard);
    }

    @Override
    public CreditCardBalanceInfoDto refundBankCard(UUID cardId, double refundSum) {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        refundBalance(creditCard, refundSum);
        creditCardRepo.save(creditCard);
        log.info("Refund credit card success: {}", creditCard.toString());
        return CreditCardMapper.INSTANCE.entityToBalanceInfoDto(creditCard);
    }

    @Override
    public CreditCardBalanceInfoDto payFromBankCard(UUID cardId, double paySum) throws NotEnoughFundsException {
        CreditCard creditCard = getCreditCardFromRepo(cardId);
        // создаем переменную и передаем данные в метод оплаты
        Boolean isPaid = pay(creditCard, paySum);
        if (!isPaid) {
            throw new NotEnoughFundsException(creditCard.toString());
        } else {
            // обновляем данные в бд
            creditCardRepo.save(creditCard);
            return CreditCardMapper.INSTANCE.entityToBalanceInfoDto(creditCard);
        }
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

    private Boolean pay(CreditCard creditCard, double paySum) {
        log.info("pay sum: {}", paySum);
        // переменные для удобства
        double debitFunds = creditCard.getDebitFunds();
        double creditFunds = creditCard.getCreditFunds();
        // высчитываем кэшбэк за покупку
        double cashback = cashback(paySum);
        // если сумма списания меньше или равна сумме собственных средств, то:
        if (paySum < debitFunds || paySum == debitFunds) {
            // списываем её из собственных средств
            creditCard.setDebitFunds(debitFunds - paySum);
            log.info("debit funds осталось до cashback: {}", debitFunds);
            // добавляем кэшбэк
            creditCard.setDebitFunds(debitFunds + cashback);
            log.info("debit funds осталось после cashback: {}", debitFunds);
            return true;
        } else {
            // если сумма списания меньше остатка кредитных средств или равна им, то:
            if (paySum < creditFunds || paySum == creditFunds) {
                // если собственные средства остались, то:
                if (debitFunds > 0) {
                    // сначала пересчитываем сумму списания с учетом остатка
                    paySum -= debitFunds;
                    log.info("спишется остаток из debit funds: {}", debitFunds);
                    log.info("будет списано из credit funds: {}", paySum);
                    log.info("будет добавлен cashback: {}", cashback);
                    // затем обнуляем остаток и добавляем кэшбэк
                    creditCard.setDebitFunds(0.0 + cashback);
                }
                // вычитаем сумму покупки из кредитных средств
                creditCard.setCreditFunds(creditFunds - paySum);
                // добавляем cashback всегда на собственные средства
                creditCard.setDebitFunds(debitFunds + cashback);
                log.info("credit funds после оплаты: {} ", creditFunds);
                log.info("debit funds после cashback: {}", debitFunds);
                return true;
                // если сумма списания больше суммы собственных и кредитных средств, то:
            } else {
                log.info("Not enough funds");
                return false;
            }
        }
    }

    /**
     * Метод высчитываем сумму кэшбэка в зависимости от суммы покупки.
     */
    private double cashback(double paySum) {
        double cashback;
        if (paySum >= 5000.0) {
            cashback = paySum * 5 / 100;
            log.info("cashback: {}", cashback);
            return cashback;
        }
        return 0.0;
    }
}
