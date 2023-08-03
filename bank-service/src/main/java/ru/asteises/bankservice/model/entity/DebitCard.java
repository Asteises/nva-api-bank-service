package ru.asteises.bankservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class DebitCard extends BankCard {

    @Id
    UUID id;
}
