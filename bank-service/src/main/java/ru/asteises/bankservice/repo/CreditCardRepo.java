package ru.asteises.bankservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.asteises.bankservice.model.entity.CreditCard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, UUID> {

    Optional<CreditCard> getCreditCardById(UUID id);

    @Query(value = "SELECT * FROM credit_cards cc WHERE cc.blocked = false", nativeQuery = true)
    List<CreditCard> getAllByBlockedFalse(boolean blocked);

    @Query(value = "SELECT * FROM credit_cards cc WHERE cc.blocked = true", nativeQuery = true)
    List<CreditCard> getAllByBlockedTrue(boolean blocked);
}
