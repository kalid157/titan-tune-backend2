package com.titan.tune.infrastructure.controller;

import com.titan.tune.domain.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats") // Chemin pour les statistiques
@RequiredArgsConstructor
public class StatsController {
    private final FavoriteService favoriteService;

    // Correspond exactement à AppEndpoint.songLikeCount
    @GetMapping("/nbrLikeForOneSong/{songTrackingId}")
    public ResponseEntity<Long> getSongLikeCount(@PathVariable String songTrackingId) {
        return ResponseEntity.ok(favoriteService.getSongLikeCount(songTrackingId));
    }
}