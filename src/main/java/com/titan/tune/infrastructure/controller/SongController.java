package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.song.CreateSongRequest;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Album;
import com.titan.tune.domain.model.Song;
import com.titan.tune.domain.model.SongEcouter;
import com.titan.tune.domain.repository.AlbumRepository;
import com.titan.tune.domain.repository.SongRepository;
import com.titan.tune.domain.service.MinioService;
import com.titan.tune.domain.service.SongEcouterService;
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
    private final AlbumRepository albumRepository;
    private final MinioService minioService;
    private final SongEcouterService songEcouterService; //  Injecté pour l'historique

    // ═══════════════════════════════════════════════════════════════
    // --- LECTURE DES CHANSONS ---
    // ═══════════════════════════════════════════════════════════════

    @GetMapping("/getAll")
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songRepository.findAll());
    }

    @GetMapping("/new")
    public ResponseEntity<List<Song>> getNewSongs() {
        //  Nécessite la méthode findTop10ByOrderByCreatedAtDesc() dans SongRepository
        return ResponseEntity.ok(songRepository.findTop10ByOrderByCreatedAtDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable String id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chanson introuvable avec l'ID : " + id));
        return ResponseEntity.ok(song);
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

    // ═══════════════════════════════════════════════════════════════
    // --- CRÉATION & UPLOAD ---
    // ═══════════════════════════════════════════════════════════════

    @PostMapping("/create")
    public ResponseEntity<Song> create(@Valid @RequestBody CreateSongRequest request) {
        Album album = albumRepository.findById(request.albumTrackingId())
                .orElseThrow(() -> new ResourceNotFoundException("Album introuvable avec l'ID : " + request.albumTrackingId()));

        Song song = Song.builder()
                .trackingId(UUID.randomUUID().toString())
                .titre(request.titre())
                .audio(request.audio())
                .albumTrackingId(request.albumTrackingId())
                .categorieTrackingId(request.categorieTrackingId())
                .artiste(album.getNomArtiste())
                .artisteTrackingId(album.getArtisteTrackingId())
                .build();
                
        return ResponseEntity.ok(songRepository.save(song));
    }

    @PostMapping("/upload")
    public ResponseEntity<Song> upload(
            @RequestParam("titre") String titre,
            @RequestParam("albumTrackingId") String albumTrackingId,
            @RequestParam(value = "categorieTrackingId", required = false) String categorieTrackingId,
            @RequestParam("audio") MultipartFile audio) {

        Album album = albumRepository.findById(albumTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Album introuvable avec l'ID : " + albumTrackingId));

        String audioUrl = minioService.uploadFile(audio);

        Song song = Song.builder()
                .trackingId(UUID.randomUUID().toString())
                .titre(titre)
                .audio(audioUrl)
                .albumTrackingId(albumTrackingId)
                .categorieTrackingId(categorieTrackingId)
                .artiste(album.getNomArtiste())
                .artisteTrackingId(album.getArtisteTrackingId())
                .build();
                
        return ResponseEntity.ok(songRepository.save(song));
    }

    // ═══════════════════════════════════════════════════════════════
    // --- SUPPRESSION ( NOUVEAU) ---
    // ═══════════════════════════════════════════════════════════════
    @DeleteMapping("/delete/{trackingIdSong}")
    public ResponseEntity<Void> deleteSong(@PathVariable String trackingIdSong) {
        if (!songRepository.existsById(trackingIdSong)) {
            throw new ResourceNotFoundException("Chanson introuvable avec l'ID : " + trackingIdSong);
        }
        
        // Optionnel : Si tu veux aussi supprimer le fichier audio de MinIO, c'est ici qu'il faudra le faire.
        
        songRepository.deleteById(trackingIdSong);
        return ResponseEntity.noContent().build(); // Renvoie un 204 (Succès)
    }

    // ═══════════════════════════════════════════════════════════════
    // --- HISTORIQUE D'ÉCOUTE (HISTORY) ---
    // ═══════════════════════════════════════════════════════════════

    @GetMapping("/history/{clientId}")
    public ResponseEntity<List<SongEcouter>> getListeningHistory(@PathVariable String clientId) {
        return ResponseEntity.ok(songEcouterService.getHistoryByUser(clientId));
    }

    @PostMapping("/history")
    public ResponseEntity<Void> saveListeningHistory(@RequestBody HistoryRequest request) {
        songEcouterService.registerListen(request.userId(), request.songId());
        return ResponseEntity.ok().build();
    }
}

//  DTO simple pour la requête d'historique
record HistoryRequest(String userId, String songId) {}