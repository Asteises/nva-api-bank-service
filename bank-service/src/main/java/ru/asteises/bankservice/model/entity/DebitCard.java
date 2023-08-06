package ru.asteises.bankservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "debit_cards")
public class DebitCard extends BankCard {

    private double balance;
}
