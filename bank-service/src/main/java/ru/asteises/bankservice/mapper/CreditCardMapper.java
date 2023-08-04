package ru.asteises.bankservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.asteises.bankservice.model.dto.CreditCardVisualDto;
import ru.asteises.bankservice.model.dto.NewCreditCardDto;
import ru.asteises.bankservice.model.entity.CreditCard;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        imports = {UUID.class})
public interface CreditCardMapper {

    CreditCardMapper INSTANCE = Mappers.getMapper(CreditCardMapper.class);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    CreditCard dtoToEntity(NewCreditCardDto newCreditCardDto);

    CreditCardVisualDto entityToVisualDto(CreditCard creditCard);

    List<CreditCardVisualDto> entityToVisualDto(List<CreditCard> creditCards);
}
