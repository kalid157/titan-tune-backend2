package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.playlist.AddSongRequest;
import com.titan.tune.application.dto.playlist.CreatePlaylistRequest;
import com.titan.tune.application.dto.playlist.PlaylistDTO;
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

    @GetMapping("/all")
    public ResponseEntity<List<PlaylistDTO>> getAll() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    @PostMapping("/create/{clientId}")
    public ResponseEntity<PlaylistDTO> create(
            @PathVariable String clientId,
            @Valid @RequestBody CreatePlaylistRequest request) {
        return ResponseEntity.ok(playlistService.createPlaylist(clientId, request));
    }

    @PostMapping("/addSong")
    public ResponseEntity<Void> addSong(@Valid @RequestBody AddSongRequest request) {
        playlistService.addSong(request.playlistId(), request.songId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{playlistId}")
    public ResponseEntity<Void> delete(@PathVariable String playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/allSongForOne/{id}")
    public ResponseEntity<List<Song>> getSongsByPlaylist(@PathVariable String id) {
        return ResponseEntity.ok(playlistService.getSongsByPlaylist(id));
    }
}