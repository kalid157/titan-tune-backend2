package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.song.SongDTO;
import com.titan.tune.domain.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping("/getAll")
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/getByAlbum/{albumId}")
    public ResponseEntity<List<SongDTO>> getByAlbum(@PathVariable String albumId) {
        return ResponseEntity.ok(songService.getSongsByAlbum(albumId));
    }

    @GetMapping("/getAllForOne/{artisteId}")
    public ResponseEntity<List<SongDTO>> getByArtiste(@PathVariable String artisteId) {
        return ResponseEntity.ok(songService.getSongsByArtiste(artisteId));
    }

     // NOUVEAUX ENDPOINTS
    @GetMapping("/get/{trackingIdSong}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable String trackingIdSong) {
        return ResponseEntity.ok(songService.getSongById(trackingIdSong));
    }

    @DeleteMapping("/delete/{trackingIdSong}")
    public ResponseEntity<Void> deleteSong(@PathVariable String trackingIdSong) {
        // NOTE: Si tu utilises MinIO, c'est ici que tu appelleras MinIO pour supprimer le fichier audio
        songService.deleteSong(trackingIdSong);
        return ResponseEntity.ok().build();
    }
}