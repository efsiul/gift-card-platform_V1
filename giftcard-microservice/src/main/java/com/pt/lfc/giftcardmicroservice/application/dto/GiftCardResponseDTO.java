package com.pt.lfc.giftcardmicroservice.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCardResponseDTO {
    private Long id;
    private String code;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
    private String status;
}