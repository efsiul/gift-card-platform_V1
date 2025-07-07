package com.pt.lfc.giftcardmicroservice.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardEvent {
    private Long giftCardId;
    private String code;
    private GiftCardStatus status;
    private String eventType;
    private LocalDateTime timestamp;
    private String payload;
}