package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.favorite.FavoriteRequest;
import com.titan.tune.domain.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoris")
@RequestMapping("/stats")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Void> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        favoriteService.addFavorite(request.clientId(), request.songId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@Valid @RequestBody FavoriteRequest request) {
        favoriteService.removeFavorite(request.clientId(), request.songId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<String>> getFavorites(@PathVariable String clientId) {
        return ResponseEntity.ok(favoriteService.getFavoriteSongIds(clientId));
    }

    // Endpoint pour le nombre de likes global
    @GetMapping("/stats/nbrLikeForOneSong/{songId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable String songId) {
        return ResponseEntity.ok(favoriteService.getSongLikeCount(songId));
    }
}
public class StatsController {
    private final FavoriteService favoriteService;

    @GetMapping("/nbrLikeForOneSong/{songTrackingId}")
    public ResponseEntity<Long> getSongLikeCount(@PathVariable String songTrackingId) {
        return ResponseEntity.ok(favoriteService.getSongLikeCount(songTrackingId));
    }
}