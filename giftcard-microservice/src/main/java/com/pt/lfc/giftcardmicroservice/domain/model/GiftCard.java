package com.pt.lfc.giftcardmicroservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCard {
    private Long id;
    private String code;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private GiftCardStatus status;
    private LocalDateTime issueDate;
    private LocalDateTime redeemDate;
}