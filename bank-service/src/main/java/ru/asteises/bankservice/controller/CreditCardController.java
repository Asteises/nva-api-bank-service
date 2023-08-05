package ru.asteises.bankservice.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<CreditCardVisualDto> addNewCreditCard(@NotNull @RequestBody NewCreditCardDto newCreditCardDto) {
        return ResponseEntity.ok(creditCardService.addNewCreditCard(newCreditCardDto));
    }

    @GetMapping("/get/{cardId}")
    public ResponseEntity<CreditCardVisualDto> getCreditCardById(@NotNull @PathVariable UUID cardId) {
        return ResponseEntity.ok(creditCardService.getCreditCardById(cardId));
    }

    @PatchMapping("/limit/{cardId}")
    public ResponseEntity<CreditCardVisualDto> changeCreditLimit(@NotNull @PathVariable UUID cardId, @RequestParam double limit) {
        return ResponseEntity.ok(creditCardService.changeCreditLimit(cardId, limit));
    }

    @PatchMapping("/block/{cardId}")
    public ResponseEntity<CreditCardVisualDto> blockCreditCardById(@NotNull @PathVariable UUID cardId) {
        return ResponseEntity.ok(creditCardService.blockCreditCardById(cardId));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<CreditCardVisualDto>> getAllNonBlockedCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllNonBlockedCreditCards());
    }

    @GetMapping("/get/all/blocked")
    public ResponseEntity<List<CreditCardVisualDto>> getAllBlockedCreditCards() {
        return ResponseEntity.ok(creditCardService.getAllBlockedCards());
    }
}
