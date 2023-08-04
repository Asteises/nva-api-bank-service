package ru.asteises.bankservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
public class CreditCard extends BankCard {

    @Column(name = "credit_limit")
    private double creditLimit;
    @Column(name = "credit_funds")
    private double creditFunds;
    @Column(name = "debit_funds")
    private double debitFunds;

    //TODO balance для кредитной карты это сумма собственных средств и кредитных

}
