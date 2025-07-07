package com.pt.lfc.giftcardmicroservice.adapter.in.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtWebFilter implements WebFilter {

    private final JwtProvider jwtProvider;

    public JwtWebFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("HEADER: " + authHeader); // <-- agrega esto

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            boolean valid = jwtProvider.validateToken(token);
            System.out.println("TOKEN VALID: " + valid); // <-- agrega esto
            System.out.println("TOKEN ROLES: " + jwtProvider.getRolesFromToken(token));

            if (valid) {
                String username = jwtProvider.getUsernameFromToken(token);
                List<SimpleGrantedAuthority> authorities = jwtProvider.getRolesFromToken(token)
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(
                                Mono.just(new SecurityContextImpl(authentication))));
            }
        }

        return chain.filter(exchange);
    }
}
