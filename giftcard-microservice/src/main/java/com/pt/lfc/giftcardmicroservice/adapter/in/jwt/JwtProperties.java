package com.pt.lfc.giftcardmicroservice.adapter.in.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    @Value("${jwt.secret}")
    private String secret;

    public String getSecret() {
        return secret;
    }
}