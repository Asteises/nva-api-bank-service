package ru.asteises.bankservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.asteises.bankservice.model.dto.DebitCardBalanceInfoDto;
import ru.asteises.bankservice.model.dto.DebitCardVisualDto;
import ru.asteises.bankservice.model.dto.NewDebitCardDto;
import ru.asteises.bankservice.model.entity.DebitCard;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        imports = {UUID.class})
public interface DebitCardMapper {

    DebitCardMapper INSTANCE = Mappers.getMapper(DebitCardMapper.class);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    DebitCard dtoToEntity(NewDebitCardDto newDebitCardDto);

    DebitCardVisualDto entityToVisualDto(DebitCard debitCard);

    List<DebitCardVisualDto> entityToVisualDto(List<DebitCard> debitCards);

    DebitCardBalanceInfoDto entityToBalanceInfoDto(DebitCard debitCard);
}
