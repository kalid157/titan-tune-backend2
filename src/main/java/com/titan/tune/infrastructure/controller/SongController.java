package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.song.CreateSongRequest;
import com.titan.tune.domain.model.Song;
import com.titan.tune.domain.repository.SongRepository;
import com.titan.tune.domain.service.MinioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {
    private final SongRepository songRepository;
    private final MinioService minioService;

    @PostMapping("/create")
    public ResponseEntity<Song> create(@Valid @RequestBody CreateSongRequest request) {
        Song song = Song.builder()
                .trackingId(UUID.randomUUID().toString())
                .titre(request.titre())
                .audio(request.audio())
                .albumTrackingId(request.albumTrackingId())
                .categorieTrackingId(request.categorieTrackingId())
                .build();
        return ResponseEntity.ok(songRepository.save(song));
    }

    @PostMapping("/upload")
    public ResponseEntity<Song> upload(
            @RequestParam("titre") String titre,
            @RequestParam("albumTrackingId") String albumTrackingId,
            @RequestParam(value = "categorieTrackingId", required = false) String categorieTrackingId,
            @RequestParam("audio") MultipartFile audio) {

        String audioUrl = minioService.uploadFile(audio);

        Song song = Song.builder()
                .trackingId(UUID.randomUUID().toString())
                .titre(titre)
                .audio(audioUrl)
                .albumTrackingId(albumTrackingId)
                .categorieTrackingId(categorieTrackingId)
                .build();
        return ResponseEntity.ok(songRepository.save(song));
    }

    @GetMapping("/getByAlbum/{trackingIdAlbum}")
    public ResponseEntity<List<Song>> getByAlbum(@PathVariable String trackingIdAlbum) {
        return ResponseEntity.ok(songRepository.findByAlbumTrackingId(trackingIdAlbum));
    }

    @GetMapping("/getAllForOne/{trackingIdArtiste}")
    public ResponseEntity<List<Song>> getByArtiste(@PathVariable String trackingIdArtiste) {
        return ResponseEntity.ok(songRepository.findByArtisteTrackingId(trackingIdArtiste));
    }
    
    @GetMapping("/getByCategorieName/{category_name}")
    public ResponseEntity<List<Song>> getByCategorieName(@PathVariable String category_name) {
        return ResponseEntity.ok(songRepository.findByCategorieName(category_name));
    }
}