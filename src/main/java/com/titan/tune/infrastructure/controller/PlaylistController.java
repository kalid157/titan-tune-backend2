package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.playlist.AddSongRequest;
import com.titan.tune.application.dto.playlist.CreatePlaylistRequest;
import com.titan.tune.domain.model.Song; // ⭐ L'IMPORT MANQUANT EST ICI
import com.titan.tune.domain.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    // ═══════════════════════════════════════════════════════════════
    // --- LECTURE & CRÉATION ---
    // ═══════════════════════════════════════════════════════════════

    @GetMapping("/all")
    public ResponseEntity<List<com.titan.tune.domain.model.Playlist>> getAll() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @PostMapping("/create/{clientId}")
    public ResponseEntity<com.titan.tune.domain.model.Playlist> create(
            @PathVariable String clientId,
            @Valid @RequestBody CreatePlaylistRequest request) {
        return ResponseEntity.ok(playlistService.createPlaylist(clientId, request));
    }

    // ═══════════════════════════════════════════════════════════════
    // --- GESTION DES CHANSONS ---
    // ═══════════════════════════════════════════════════════════════

    @PostMapping("/addSong")
    public ResponseEntity<Void> addSong(@Valid @RequestBody AddSongRequest request) {
        playlistService.addSong(request.playlistId(), request.songId());
        return ResponseEntity.ok().build();
    }

    //  C'est cette méthode qui utilisait "Song" sans l'importer
    @GetMapping("/allSongForOne/{id}")
    public ResponseEntity<List<Song>> getSongsByPlaylist(@PathVariable String id) {
        return ResponseEntity.ok(playlistService.getSongsByPlaylist(id));
    }

    // ═══════════════════════════════════════════════════════════════
    // --- SUPPRESSION ---
    // ═══════════════════════════════════════════════════════════════

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}