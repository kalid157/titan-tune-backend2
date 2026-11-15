package com.titan.tune.domain.service;

import com.titan.tune.application.dto.auth.AuthResponse;
import com.titan.tune.application.dto.auth.LoginRequest;
import com.titan.tune.application.dto.auth.RegisterRequest;
import com.titan.tune.common.exception.BusinessException;
import com.titan.tune.domain.model.User;
import com.titan.tune.domain.repository.UserRepository;
import com.titan.tune.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Cet email est déjà utilisé.");
        }

        User user = User.builder()
                .trackingId(UUID.randomUUID().toString())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getTrackingId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getIsPremium());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Email ou mot de passe incorrect."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Email ou mot de passe incorrect.");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getTrackingId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getIsPremium());
    }
}