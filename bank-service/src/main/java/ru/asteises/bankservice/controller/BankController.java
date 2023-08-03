package ru.asteises.bankservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCardDto;
import ru.asteises.bankservice.service.impl.BankServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/client_service")
public class BankController {

    private final BankServiceImpl bankService;

    @PostMapping("/add_new_credit_card")
    public ResponseEntity<CreditCardVisualDto> addNewCard(@RequestBody NewCardDto newCardDto) {
        return ResponseEntity.ok(bankService.addNewCard(newCardDto));
    }
}
