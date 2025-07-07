package com.pt.lfc.usermicroservice.domain.service;

import com.pt.lfc.usermicroservice.domain.model.User;
import com.pt.lfc.usermicroservice.domain.port.in.AuthenticationServicePort;
import com.pt.lfc.usermicroservice.domain.port.out.UserRepositoryPort;
import com.pt.lfc.usermicroservice.domain.port.out.PasswordEncoderPort;
import com.pt.lfc.usermicroservice.domain.port.out.JwtProviderPort;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationServicePort {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtProviderPort jwtProvider;

    public AuthenticationServiceImpl(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            JwtProviderPort jwtProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return jwtProvider.generateToken(user);
    }

    @Override
    public User validateToken(String token) {
        return jwtProvider.validateToken(token);
    }
}
