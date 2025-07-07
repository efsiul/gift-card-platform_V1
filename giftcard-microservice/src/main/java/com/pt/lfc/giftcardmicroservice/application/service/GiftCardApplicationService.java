package com.pt.lfc.giftcardmicroservice.application.service;

import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardRequestDTO;
import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardResponseDTO;
import com.pt.lfc.giftcardmicroservice.application.mapper.GiftCardMapper;
import com.pt.lfc.giftcardmicroservice.application.port.in.GiftCardApplicationServicePort;
import com.pt.lfc.giftcardmicroservice.domain.port.in.GiftCardServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GiftCardApplicationService implements GiftCardApplicationServicePort {

    private final GiftCardServicePort giftCardServicePort;
    private final GiftCardMapper giftCardMapper;

    public Mono<GiftCardResponseDTO> createGiftCard(GiftCardRequestDTO requestDTO) {
        return giftCardServicePort.createGiftCard(giftCardMapper.toDomain(requestDTO))
                .map(giftCardMapper::toResponseDTO);
    }

    public Mono<GiftCardResponseDTO> getGiftCardById(Long id) {
        return giftCardServicePort.getGiftCardById(id)
                .map(giftCardMapper::toResponseDTO);
    }

    public Flux<GiftCardResponseDTO> getAllGiftCards() {
        return giftCardServicePort.getAllGiftCards()
                .map(giftCardMapper::toResponseDTO);
    }

    public Mono<GiftCardResponseDTO> updateGiftCard(Long id, GiftCardRequestDTO requestDTO) {
        return giftCardServicePort.updateGiftCard(id, giftCardMapper.toDomain(requestDTO))
                .map(giftCardMapper::toResponseDTO);
    }

    public Mono<Void> deleteGiftCard(Long id) {
        return giftCardServicePort.deleteGiftCard(id);
    }

    public Mono<GiftCardResponseDTO> emitGiftCard(Long id) {
        return giftCardServicePort.emitGiftCard(id)
                .map(giftCardMapper::toResponseDTO);
    }

    public Mono<GiftCardResponseDTO> redeemGiftCard(Long id, Long userId, Double amount) {
        return giftCardServicePort.redeemGiftCard(id, userId, amount)
                .map(giftCardMapper::toResponseDTO);
    }

    public Mono<GiftCardResponseDTO> blockGiftCard(Long id, String reason) {
        return giftCardServicePort.blockGiftCard(id, reason)
                .map(giftCardMapper::toResponseDTO);
    }

    public Mono<GiftCardResponseDTO> getGiftCardByCode(String code) {
        return giftCardServicePort.getGiftCardByCode(code)
                .map(giftCardMapper::toResponseDTO);
    }

    // Puedes agregar otros métodos según los casos de uso de negocio.
}