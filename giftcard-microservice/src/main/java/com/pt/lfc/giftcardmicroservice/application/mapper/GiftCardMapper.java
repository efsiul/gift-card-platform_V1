package com.pt.lfc.giftcardmicroservice.application.mapper;

import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardRequestDTO;
import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardResponseDTO;
import com.pt.lfc.giftcardmicroservice.domain.model.GiftCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GiftCardMapper {
    GiftCard toDomain(GiftCardRequestDTO dto);

    GiftCardResponseDTO toResponseDTO(GiftCard model);
}
