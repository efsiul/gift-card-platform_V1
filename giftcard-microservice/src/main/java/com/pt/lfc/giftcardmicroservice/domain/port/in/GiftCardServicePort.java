package com.pt.lfc.giftcardmicroservice.domain.port.in;

import com.pt.lfc.giftcardmicroservice.domain.model.GiftCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GiftCardServicePort {
    // Core CRUD
    Mono<GiftCard> createGiftCard(GiftCard giftCard);
    Mono<GiftCard> getGiftCardById(Long id);
    Flux<GiftCard> getAllGiftCards();
    Mono<GiftCard> updateGiftCard(Long id, GiftCard giftCard);
    Mono<Void> deleteGiftCard(Long id);

    // Business Use Cases
    Mono<GiftCard> emitGiftCard(Long id);        // Issue/activate the gift card
    Mono<GiftCard> redeemGiftCard(Long id, Long userId, Double amount); // Redeem amount from the card
    Mono<GiftCard> blockGiftCard(Long id, String reason); // Block (e.g. for fraud)
    Mono<GiftCard> sendNotification(Long id, String message); // Send notification for this card

    // Maybe search by code
    Mono<GiftCard> getGiftCardByCode(String code);
}