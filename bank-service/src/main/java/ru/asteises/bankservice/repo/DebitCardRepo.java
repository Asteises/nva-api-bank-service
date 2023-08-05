package ru.asteises.bankservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asteises.bankservice.model.entity.DebitCard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DebitCardRepo extends JpaRepository<DebitCard, UUID> {
    Optional<DebitCard> getDebitCardById(UUID cardId);
    List<DebitCard> getAllByBlockedFalse();
    List<DebitCard> getAllByBlockedTrue();
}
