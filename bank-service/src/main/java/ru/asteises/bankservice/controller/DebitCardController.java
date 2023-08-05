package ru.asteises.bankservice.controller;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asteises.bankservice.model.dto.CreditCardBalanceInfoDto;
import ru.asteises.bankservice.model.dto.DebitCardBalanceInfoDto;
import ru.asteises.bankservice.model.dto.DebitCardVisualDto;
import ru.asteises.bankservice.model.dto.NewDebitCardDto;
import ru.asteises.bankservice.service.DebitCardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bank/debit/card")
public class DebitCardController {

    private final DebitCardService debitCardService;

    @PostMapping("/add")
    public ResponseEntity<DebitCardVisualDto> addNewDebitCard(@NotNull @RequestBody NewDebitCardDto newDebitCardDto) {
        return ResponseEntity.ok(debitCardService.addNewDebitCard(newDebitCardDto));
    }

    @GetMapping("/get/{cardId}")
    public ResponseEntity<DebitCardVisualDto> getDebitCardById(@NotNull @PathVariable UUID cardId) {
        return ResponseEntity.ok(debitCardService.getDebitCardById(cardId));
    }

    @PatchMapping("/block/{cardId}")
    public ResponseEntity<DebitCardVisualDto> blockDebitCardById(@NotNull @PathVariable UUID cardId) {
        return ResponseEntity.ok(debitCardService.blockDebitCardById(cardId));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<DebitCardVisualDto>> getAllNonBlockedDebitCards() {
        return ResponseEntity.ok(debitCardService.getAllNonBlockedDebitCards());
    }

    @GetMapping("/get/all/blocked")
    public ResponseEntity<List<DebitCardVisualDto>> getAllBlockedDebitCards() {
        return ResponseEntity.ok(debitCardService.getAllBlockedDebitCards());
    }

    @GetMapping("/balance/info/{cardId}")
    public ResponseEntity<DebitCardBalanceInfoDto> showDebitCardBalanceInfo(@NonNull @PathVariable UUID cardId) {
        return ResponseEntity.ok((DebitCardBalanceInfoDto) debitCardService.showBankCardBalanceInfo(cardId));
    }

    @PatchMapping("/balance/refund/{cardId}")
    public ResponseEntity<DebitCardBalanceInfoDto> refundDebitCard(@NonNull @PathVariable UUID cardId,
                                                                     @RequestParam double refundSum) {
        return ResponseEntity.ok((DebitCardBalanceInfoDto) debitCardService.refundBankCard(cardId, refundSum));
    }

    @PatchMapping("/balance/pay/{cardId}")
    public ResponseEntity<DebitCardBalanceInfoDto> payFromDebitCard(@NonNull @PathVariable UUID cardId,
                                                                      @RequestParam double paySum) {
        return ResponseEntity.ok((DebitCardBalanceInfoDto) debitCardService.payFromBankCard(cardId, paySum));
    }
}