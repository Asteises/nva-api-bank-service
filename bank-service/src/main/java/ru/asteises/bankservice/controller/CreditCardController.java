package ru.asteises.bankservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCreditCardDto;
import ru.asteises.bankservice.service.impl.CreditCardServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bank/credit/card")
public class CreditCardController {

    private final CreditCardServiceImpl creditCardService;

    @PostMapping("/add")
    public ResponseEntity<CreditCardVisualDto> addNewCreditCard(@RequestBody NewCreditCardDto newCreditCardDto) {
        return ResponseEntity.ok(creditCardService.addNewCreditCard(newCreditCardDto));
    }

    @GetMapping("/get/{cardId}")
    public ResponseEntity<CreditCardVisualDto> getCreditCardById(@PathVariable UUID cardId) {
        return ResponseEntity.ok(creditCardService.getCreditCardById(cardId));
    }

    @PatchMapping("/limit/{cardId}")
    public ResponseEntity<CreditCardVisualDto> changeCreditLimit(@PathVariable UUID cardId, @RequestParam double limit) {
        return ResponseEntity.ok(creditCardService.changeCreditLimit(cardId, limit));
    }

    @PatchMapping("/block/{cardId}")
    public ResponseEntity<CreditCardVisualDto> blockCreditCardById(@PathVariable UUID cardId) {
        return ResponseEntity.ok(creditCardService.blockCreditCardById(cardId));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<CreditCardVisualDto>> getAllNonBlockedCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllNonBlockedCreditCards());
    }

    @GetMapping("/get/all/blocked")
    public ResponseEntity<List<CreditCardVisualDto>> getAllBlockedCards() {
        return ResponseEntity.ok(creditCardService.getAllBlockedCards());
    }
}
