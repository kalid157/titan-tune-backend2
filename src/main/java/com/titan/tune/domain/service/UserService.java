package com.titan.tune.domain.service;

import com.titan.tune.application.dto.auth.ArtistResponse;
import com.titan.tune.application.dto.auth.ClientResponse;
import com.titan.tune.application.dto.auth.RegisterArtistRequest;
import com.titan.tune.common.exception.BusinessException;
import com.titan.tune.domain.model.User;
import com.titan.tune.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public List<ClientResponse> getAllClients() {
        return userRepository.findByRole("USER").stream()
                .map(user -> new ClientResponse(
                        user.getTrackingId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone()
                ))
                .collect(Collectors.toList());
    }

    public ArtistResponse registerArtist(RegisterArtistRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Cet email est déjà utilisé.");
        }

        User artist = User.builder()
                .trackingId(UUID.randomUUID().toString())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .alias(request.alias())
                .phone(request.phone())
                .description(request.description())
                .role("ARTIST") //  On définit le rôle ARTIST
                .build();

        userRepository.save(artist);
        return mapToArtistResponse(artist);
    }

    public List<ArtistResponse> getAllArtists() {
        return userRepository.findByRole("ARTIST").stream()
                .map(this::mapToArtistResponse)
                .collect(Collectors.toList());
    }

    private ArtistResponse mapToArtistResponse(User user) {
        return new ArtistResponse(
            user.getTrackingId(), user.getFirstName(), user.getLastName(),
            user.getAlias(), user.getEmail(), user.getDescription()
        );
    }
}