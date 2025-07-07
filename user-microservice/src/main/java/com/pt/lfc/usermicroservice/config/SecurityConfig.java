package com.pt.lfc.usermicroservice.config;

import com.pt.lfc.usermicroservice.adapter.in.jwt.JwtAuthorizationFilter;
import com.pt.lfc.usermicroservice.domain.port.out.JwtProviderPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProviderPort jwtProviderPort;

    public SecurityConfig(JwtProviderPort jwtProviderPort) {
        this.jwtProviderPort = jwtProviderPort;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/registro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios/internalLogin").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/usuarios/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/usuarios/{id}").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/usuarios/roles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/usuarios/add-role-to-user", "/api/v1/usuarios/add-role-to-user/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/usuarios/remove-role-to-user", "/api/v1/usuarios/remove-role-to-user/**").hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthorizationFilter(jwtProviderPort), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}