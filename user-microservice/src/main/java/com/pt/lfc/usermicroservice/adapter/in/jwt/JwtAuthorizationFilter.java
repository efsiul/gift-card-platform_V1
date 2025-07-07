package com.pt.lfc.usermicroservice.adapter.in.jwt;

import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.port.out.JwtProviderPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtProviderPort jwtProviderPort;

    public JwtAuthorizationFilter(JwtProviderPort jwtProviderPort) {
        this.jwtProviderPort = jwtProviderPort;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/api/v1/usuarios/login")
                || path.equals("/api/v1/usuarios/registro")
                || path.equals("/api/v1/usuarios/internalLogin");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(BEARER_PREFIX.length());

        try {
            User user = jwtProviderPort.validateToken(token);

            List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            // 🔍 Única línea nueva añadida: LOG para ver las autoridades reales asignadas
            System.out.println("🔑 Authorities del usuario autenticado: " + authorities);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Token inválido, no setea autenticación
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
