package ru.asteises.bankservice.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asteises.bankservice.model.dto.CreditCardBalanceInfoDto;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCreditCardDto;
import ru.asteises.bankservice.service.CreditCardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bank/credit/card")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping("/add")
    public ResponseEntity<CreditCardVisualDto> addNewCreditCard(@NonNull @RequestBody NewCreditCardDto newCreditCardDto) {
        return ResponseEntity.ok(creditCardService.addNewCreditCard(newCreditCardDto));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CreditCardVisualDto> getCreditCardById(@NonNull @PathVariable UUID cardId) {
        return ResponseEntity.ok(creditCardService.getCreditCardById(cardId));
    }

    @PatchMapping("/limit/{cardId}")
    public ResponseEntity<CreditCardVisualDto> changeCreditLimit(@NonNull @PathVariable UUID cardId,
                                                                 @RequestParam double limit) {
        return ResponseEntity.ok(creditCardService.changeCreditLimit(cardId, limit));
    }

    @PatchMapping("/block/{cardId}")
    public ResponseEntity<CreditCardVisualDto> blockCreditCardById(@NonNull @PathVariable UUID cardId) {
        return ResponseEntity.ok(creditCardService.blockCreditCardById(cardId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CreditCardVisualDto>> getAllNonBlockedCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllNonBlockedCreditCards());
    }

    @GetMapping("/all/blocked")
    public ResponseEntity<List<CreditCardVisualDto>> getAllBlockedCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllBlockedCards());
    }

    @GetMapping("/balance/info/{cardId}")
    public ResponseEntity<CreditCardBalanceInfoDto> showCreditCardBalanceInfo(@NonNull @PathVariable UUID cardId) {
        return ResponseEntity.ok((CreditCardBalanceInfoDto) creditCardService.showBankCardBalanceInfo(cardId));
    }

    @PatchMapping("/balance/refund/{cardId}")
    public ResponseEntity<CreditCardBalanceInfoDto> refundCreditCard(@NonNull @PathVariable UUID cardId,
                                                                     @RequestParam double refundSum) {
        return ResponseEntity.ok((CreditCardBalanceInfoDto) creditCardService.refundBankCard(cardId, refundSum));
    }

    @PatchMapping("/balance/pay/{cardId}")
    public ResponseEntity<CreditCardBalanceInfoDto> payFromCreditCard(@NonNull @PathVariable UUID cardId,
                                                                      @RequestParam double paySum) {
        return ResponseEntity.ok((CreditCardBalanceInfoDto) creditCardService.payFromBankCard(cardId, paySum));
    }
}
