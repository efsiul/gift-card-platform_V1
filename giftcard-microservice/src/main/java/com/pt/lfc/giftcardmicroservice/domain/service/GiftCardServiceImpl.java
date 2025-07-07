package com.pt.lfc.giftcardmicroservice.domain.service;

import com.pt.lfc.giftcardmicroservice.domain.model.GiftCard;
import com.pt.lfc.giftcardmicroservice.domain.model.GiftCardEvent;
import com.pt.lfc.giftcardmicroservice.domain.model.GiftCardStatus;
import com.pt.lfc.giftcardmicroservice.domain.port.in.GiftCardServicePort;
import com.pt.lfc.giftcardmicroservice.domain.port.out.GiftCardEventPublisherPort;
import com.pt.lfc.giftcardmicroservice.domain.port.out.GiftCardRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GiftCardServiceImpl implements GiftCardServicePort {

    private final GiftCardRepositoryPort giftCardRepositoryPort;
    private final GiftCardEventPublisherPort eventPublisherPort;

    @Override
    public Mono<GiftCard> createGiftCard(GiftCard giftCard) {
        giftCard.setStatus(GiftCardStatus.CREATED);
        giftCard.setCreationDate(LocalDateTime.now());
        if (giftCard.getIssueDate() == null) {
            giftCard.setIssueDate(LocalDateTime.now());
        }
        return giftCardRepositoryPort.save(giftCard);
    }

    @Override
    public Mono<GiftCard> getGiftCardById(Long id) {
        return giftCardRepositoryPort.findById(id);
    }

    @Override
    public Flux<GiftCard> getAllGiftCards() {
        return giftCardRepositoryPort.findAll();
    }

    @Override
    public Mono<GiftCard> updateGiftCard(Long id, GiftCard updated) {
        return giftCardRepositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setAmount(updated.getAmount());
                    existing.setExpirationDate(updated.getExpirationDate());
                    return giftCardRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteGiftCard(Long id) {
        return giftCardRepositoryPort.deleteById(id);
    }

    @Override
    public Mono<GiftCard> emitGiftCard(Long id) {
        return giftCardRepositoryPort.findById(id)
                .flatMap(card -> {
                    card.setStatus(GiftCardStatus.ISSUED);
                    if (card.getIssueDate() == null) {
                        card.setIssueDate(LocalDateTime.now());
                    }
                    return giftCardRepositoryPort.save(card)
                            .flatMap(saved -> {
                                GiftCardEvent event = GiftCardEvent.builder()
                                        .giftCardId(saved.getId())
                                        .code(saved.getCode())
                                        .status(saved.getStatus())
                                        .eventType("ISSUED")
                                        .timestamp(LocalDateTime.now())
                                        .payload(null)
                                        .build();
                                return eventPublisherPort.publishEvent(event).thenReturn(saved);
                            });
                });
    }

    @Override
    public Mono<GiftCard> redeemGiftCard(Long id, Long userId, Double amount) {
        return giftCardRepositoryPort.findById(id)
                .flatMap(card -> {
                    // Validation: status, expiration, balance, etc.
                    if (card.getStatus() != GiftCardStatus.ISSUED) {
                        return Mono.error(new IllegalStateException("Card is not active"));
                    }
                    if (card.getExpirationDate().isBefore(LocalDateTime.now())) {
                        card.setStatus(GiftCardStatus.EXPIRED);
                        return giftCardRepositoryPort.save(card)
                                .then(Mono.error(new IllegalStateException("Card is expired")));
                    }
                    BigDecimal amountBD = BigDecimal.valueOf(amount);
                    if (card.getAmount().compareTo(amountBD) < 0) {
                        return Mono.error(new IllegalArgumentException("Insufficient balance"));
                    }
                    card.setAmount(card.getAmount().subtract(amountBD));
                    if (card.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                        card.setStatus(GiftCardStatus.REDEEMED);
                        card.setRedeemDate(LocalDateTime.now()); // <-- CORREGIDO
                    }
                    return giftCardRepositoryPort.save(card)
                            .flatMap(saved -> {
                                GiftCardEvent event = GiftCardEvent.builder()
                                        .giftCardId(saved.getId())
                                        .code(saved.getCode())
                                        .status(saved.getStatus())
                                        .eventType("REDEEMED")
                                        .timestamp(LocalDateTime.now())
                                        .payload("UserId:" + userId + ", amount:" + amount)
                                        .build();
                                return eventPublisherPort.publishEvent(event).thenReturn(saved);
                            });
                });
    }

    @Override
    public Mono<GiftCard> blockGiftCard(Long id, String reason) {
        return giftCardRepositoryPort.findById(id)
                .flatMap(card -> {
                    card.setStatus(GiftCardStatus.EXPIRED);
                    return giftCardRepositoryPort.save(card);
                });
    }

    @Override
    public Mono<GiftCard> sendNotification(Long id, String message) {
        return giftCardRepositoryPort.findById(id);
    }

    @Override
    public Mono<GiftCard> getGiftCardByCode(String code) {
        return giftCardRepositoryPort.findByCode(code);
    }
}
