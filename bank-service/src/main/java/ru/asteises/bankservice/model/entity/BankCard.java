package ru.asteises.bankservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@ToString
public abstract class BankCard {

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "blocked")
    private boolean isBlocked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return Objects.equals(id, bankCard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
