package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.auth.*;
import com.titan.tune.domain.model.User;
import com.titan.tune.domain.repository.UserRepository;
import com.titan.tune.domain.service.AuthService;
import com.titan.tune.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// ⭐ J'ai retiré @RequestMapping("/user") pour gérer les routes /auth/ et /user/ séparément
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository; // ⭐ Ajouté pour le profil

    // ═══════════════════════════════════════════════════════════════
    // --- AUTHENTIFICATION ---
    // ═══════════════════════════════════════════════════════════════

    @PostMapping("/user/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/user/registerClient")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken() {
        // TODO: Implémenter la logique de rafraîchissement du token JWT
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout() {
        // TODO: Implémenter la déconnexion (ex: blacklister le token si nécessaire)
        return ResponseEntity.ok().build();
    }

    // ═══════════════════════════════════════════════════════════════
    // --- PROFIL UTILISATEUR ---
    // ═══════════════════════════════════════════════════════════════

    @GetMapping("/user/profile/{clientId}")
    public ResponseEntity<User> getProfile(@PathVariable String clientId) {
        User user = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + clientId));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User updatedUser) {
        // TODO: Implémenter la mise à jour du profil
        return ResponseEntity.ok().build();
    }

    // ═══════════════════════════════════════════════════════════════
    // --- GESTION DES ARTISTES & CLIENTS ---
    // ═══════════════════════════════════════════════════════════════

    @PostMapping("/user/registerArtist")
    public ResponseEntity<ArtistResponse> registerArtist(@Valid @RequestBody RegisterArtistRequest request) {
        return ResponseEntity.ok(userService.registerArtist(request));
    }

    @GetMapping("/user/allArtist")
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        return ResponseEntity.ok(userService.getAllArtists());
    }

    @GetMapping("/user/allClient")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.ok(userService.getAllClients());
    }
}