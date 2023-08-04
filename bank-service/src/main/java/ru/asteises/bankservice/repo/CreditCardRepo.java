package ru.asteises.bankservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.asteises.bankservice.model.entity.CreditCard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, UUID> {

    Optional<CreditCard> getCreditCardById(UUID id);
    List<CreditCard> getAllByBlockedFalse();
    List<CreditCard> getAllByBlockedTrue();
}
