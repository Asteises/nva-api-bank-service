package ru.asteises.bankservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_cards")
public class CreditCard extends BankCard {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "credit_limit")
    private double creditLimit;
    @Column(name = "credit_funds")
    private double creditFunds;
    @Column(name = "debit_funds")
    private double debitFunds;

    //TODO Почему нельзя указать поля для БД в предке?
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "balance")
    private double balance;
    @Column(name = "is_blocked")
    private boolean isBlocked;

    //TODO balance для кредитной карты это сумма собственных средств и кредитных


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
