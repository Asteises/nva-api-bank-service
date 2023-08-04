package ru.asteises.bankservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<DebitCardVisualDto> addNewCreditCard(@RequestBody NewDebitCardDto newDebitCardDto) {
        return ResponseEntity.ok(debitCardService.addNewCreditCard(newDebitCardDto));
    }

    @GetMapping("/get/{cardId}")
    public ResponseEntity<DebitCardVisualDto> getCreditCardById(@PathVariable UUID cardId) {
        return ResponseEntity.ok(debitCardService.getCreditCardById(cardId));
    }

    @PatchMapping("/block/{cardId}")
    public ResponseEntity<DebitCardVisualDto> blockCreditCardById(@PathVariable UUID cardId) {
        return ResponseEntity.ok(debitCardService.blockCreditCardById(cardId));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<DebitCardVisualDto>> getAllNonBlockedCreditCards() {
        return ResponseEntity.ok(debitCardService.getAllNonBlockedCreditCards());
    }

    @GetMapping("/get/all/blocked")
    public ResponseEntity<List<DebitCardVisualDto>> getAllBlockedCards() {
        return ResponseEntity.ok(debitCardService.getAllBlockedCards());
    }
}