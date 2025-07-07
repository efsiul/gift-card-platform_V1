package com.pt.lfc.giftcardmicroservice.application.port.in;

import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardRequestDTO;
import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GiftCardApplicationServicePort {
    Mono<GiftCardResponseDTO> createGiftCard(GiftCardRequestDTO requestDTO);
    Mono<GiftCardResponseDTO> getGiftCardById(Long id);
    Flux<GiftCardResponseDTO> getAllGiftCards();
    Mono<GiftCardResponseDTO> updateGiftCard(Long id, GiftCardRequestDTO requestDTO);
    Mono<Void> deleteGiftCard(Long id);
    Mono<GiftCardResponseDTO> emitGiftCard(Long id);
    Mono<GiftCardResponseDTO> redeemGiftCard(Long id, Long userId, Double amount);
    Mono<GiftCardResponseDTO> blockGiftCard(Long id, String reason);
    Mono<GiftCardResponseDTO> getGiftCardByCode(String code);
}