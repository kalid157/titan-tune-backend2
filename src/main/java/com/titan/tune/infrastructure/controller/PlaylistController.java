package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.playlist.AddSongRequest;
import com.titan.tune.application.dto.playlist.CreatePlaylistRequest;
import com.titan.tune.application.dto.playlist.PlaylistDTO;
import com.titan.tune.domain.model.Playlist;
import com.titan.tune.domain.model.Song;
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

    // 1. GET /playlist/all
    @GetMapping("/all")
    public ResponseEntity<List<PlaylistDTO>> getAll() {
        return ResponseEntity.ok(playlistService.getAllPlaylists());
    }

    // 2. POST /playlist/create/{clientId}
    @PostMapping("/create/{clientId}")
    public ResponseEntity<PlaylistDTO> create(
            @PathVariable String clientId,
            @Valid @RequestBody CreatePlaylistRequest request) {
        return ResponseEntity.ok(playlistService.createPlaylist(clientId, request));
    }

    // 3. POST /playlist/addSong
    @PostMapping("/addSong")
    public ResponseEntity<Void> addSong(@Valid @RequestBody AddSongRequest request) {
        playlistService.addSong(request.playlistId(), request.songId());
        return ResponseEntity.ok().build();
    }

    // 4. DELETE /playlist/delete/{playlistId}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    // 5. GET /playlist/{playlistId} (Détails de la playlist)
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDTO> getPlaylistDetails(@PathVariable String id) {
        return ResponseEntity.ok(playlistService.getPlaylistById(id));
    }

    // 6. GET /playlist/{playlistId}/songs ET /playlist/allSongForOne/{playlistId}
    @GetMapping({"/{id}/songs", "/allSongForOne/{id}"})
    public ResponseEntity<List<Song>> getSongsByPlaylist(@PathVariable String id) {
        return ResponseEntity.ok(playlistService.getSongsByPlaylist(id));
    }
}