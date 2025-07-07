package com.pt.lfc.giftcardmicroservice.config;

import com.pt.lfc.giftcardmicroservice.adapter.in.jwt.JwtWebFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtWebFilter jwtWebFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/ping").permitAll()
                        .pathMatchers("/pt-lfc/api/v1/giftcards/**").hasRole("ADMIN")
                        .pathMatchers("/api/v1/giftcards/**").authenticated()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        // Todo permitido para probar la app sin seguridad
//        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(ex -> ex.anyExchange().permitAll())
//                .build();
//    }


//}