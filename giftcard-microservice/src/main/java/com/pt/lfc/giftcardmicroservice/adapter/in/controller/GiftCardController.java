package com.pt.lfc.giftcardmicroservice.adapter.in.controller;

import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardRequestDTO;
import com.pt.lfc.giftcardmicroservice.application.dto.GiftCardResponseDTO;
import com.pt.lfc.giftcardmicroservice.application.port.in.GiftCardApplicationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pt-lfc/api/v1/giftcards")
@RequiredArgsConstructor
public class GiftCardController {

    private final GiftCardApplicationServicePort giftCardService;

    @PostMapping
    public Mono<GiftCardResponseDTO> createGiftCard(@RequestBody GiftCardRequestDTO dto) {
        return giftCardService.createGiftCard(dto);
    }

    @GetMapping("/{id}")
    public Mono<GiftCardResponseDTO> getGiftCardById(@PathVariable("id") Long id) {
        return giftCardService.getGiftCardById(id);
    }

    @GetMapping
    public Flux<GiftCardResponseDTO> getAllGiftCards() {
        return giftCardService.getAllGiftCards();
    }

    @PutMapping("/{id}")
    public Mono<GiftCardResponseDTO> updateGiftCard(@PathVariable("id") Long id, @RequestBody GiftCardRequestDTO dto) {
        return giftCardService.updateGiftCard(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteGiftCard(@PathVariable("id") Long id) {
        return giftCardService.deleteGiftCard(id);
    }

    @PostMapping("/{id}/emit")
    public Mono<GiftCardResponseDTO> emitGiftCard(@PathVariable("id") Long id) {
        return giftCardService.emitGiftCard(id);
    }

    @PostMapping("/{id}/redeem")
    public Mono<GiftCardResponseDTO> redeemGiftCard(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            @RequestParam("amount") Double amount) {
        return giftCardService.redeemGiftCard(id, userId, amount);
    }

    @PostMapping("/{id}/block")
    public Mono<GiftCardResponseDTO> blockGiftCard(
            @PathVariable("id") Long id,
            @RequestParam("reason") String reason) {
        return giftCardService.blockGiftCard(id, reason);
    }

    @GetMapping("/by-code/{code}")
    public Mono<GiftCardResponseDTO> getGiftCardByCode(@PathVariable("code") String code) {
        return giftCardService.getGiftCardByCode(code);
    }

}
