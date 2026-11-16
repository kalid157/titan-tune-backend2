package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.auth.*;
import com.titan.tune.domain.service.AuthService;
import com.titan.tune.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/registerClient")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    //  NOUVEAUX ENDPOINTS POUR LES ARTISTES
    @PostMapping("/registerArtist")
    public ResponseEntity<ArtistResponse> registerArtist(@Valid @RequestBody RegisterArtistRequest request) {
        return ResponseEntity.ok(userService.registerArtist(request));
    }

    @GetMapping("/allArtist")
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        return ResponseEntity.ok(userService.getAllArtists());
    }
}