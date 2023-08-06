package ru.asteises.bankservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.asteises.bankservice.model.entity.DebitCard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DebitCardRepo extends JpaRepository<DebitCard, UUID> {
    Optional<DebitCard> getDebitCardById(UUID cardId);
    @Query(value = "SELECT * FROM debit_cards dc WHERE dc.blocked = false", nativeQuery = true)
    List<DebitCard> getAllByBlockedFalse();
    @Query(value = "SELECT * FROM debit_cards dc WHERE dc.blocked = true", nativeQuery = true)
    List<DebitCard> getAllByBlockedTrue();
}
