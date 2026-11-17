package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.album.CreateAlbumRequest;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Album;
import com.titan.tune.domain.model.User;
import com.titan.tune.domain.repository.AlbumRepository;
import com.titan.tune.domain.repository.UserRepository;
import com.titan.tune.domain.service.MinioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {
    
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final MinioService minioService;

    // ═══════════════════════════════════════════════════════════════
    // --- LECTURE DES ALBUMS ---
    // ═══════════════════════════════════════════════════════════════

    //  ENDPOINT AJOUTÉ : Liste de tous les albums
    @GetMapping("/all")
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(albumRepository.findAll());
    }

    @GetMapping("/get/{trackingId}")
    public ResponseEntity<Album> getById(@PathVariable String trackingId) {
        Album album = albumRepository.findById(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Album introuvable avec l'ID : " + trackingId));
        return ResponseEntity.ok(album);
    }

    // ═══════════════════════════════════════════════════════════════
    // --- CRÉATION & UPLOAD ---
    // ═══════════════════════════════════════════════════════════════

    @PostMapping("/create")
    public ResponseEntity<Album> create(@Valid @RequestBody CreateAlbumRequest request) {
        User artist = userRepository.findById(request.artisteTrackingId())
                .orElseThrow(() -> new ResourceNotFoundException("Artiste introuvable avec l'ID : " + request.artisteTrackingId()));

        String artistName = (artist.getAlias() != null && !artist.getAlias().isEmpty()) 
                ? artist.getAlias() 
                : artist.getFirstName() + " " + artist.getLastName();

        Album album = Album.builder()
                .trackingId(UUID.randomUUID().toString())
                .titreAlbum(request.titreAlbum())
                .artisteTrackingId(request.artisteTrackingId())
                .nomArtiste(artistName)
                .imageAlbum(request.imageAlbum())
                .build();
                
        return ResponseEntity.ok(albumRepository.save(album));
    }

    @PostMapping("/upload")
    public ResponseEntity<Album> upload(
            @RequestParam("titreAlbum") String titreAlbum,
            @RequestParam("artisteTrackingId") String artisteTrackingId,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        User artist = userRepository.findById(artisteTrackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Artiste introuvable"));
        
        String artistName = (artist.getAlias() != null && !artist.getAlias().isEmpty()) 
                ? artist.getAlias() 
                : artist.getFirstName() + " " + artist.getLastName();

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = minioService.uploadFile(image);
        }

        Album album = Album.builder()
                .trackingId(UUID.randomUUID().toString())
                .titreAlbum(titreAlbum)
                .artisteTrackingId(artisteTrackingId)
                .nomArtiste(artistName)
                .imageAlbum(imageUrl)
                .build();
                
        return ResponseEntity.ok(albumRepository.save(album));
    }

    @PutMapping("/updateImage/{trackingId}")
    public ResponseEntity<Album> updateImage(
            @PathVariable String trackingId,
            @RequestParam("image") MultipartFile image) {
        Album album = albumRepository.findById(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Album introuvable"));
        String imageUrl = minioService.uploadFile(image);
        album.setImageAlbum(imageUrl);
        return ResponseEntity.ok(albumRepository.save(album));
    }

    // ═══════════════════════════════════════════════════════════════
    // --- SUPPRESSION ---
    // ═══════════════════════════════════════════════════════════════

    //  ENDPOINT AJOUTÉ : Supprimer un album
    @DeleteMapping("/delete/{trackingId}")
    public ResponseEntity<Void> delete(@PathVariable String trackingId) {
        if (!albumRepository.existsById(trackingId)) {
            throw new ResourceNotFoundException("Album introuvable avec l'ID : " + trackingId);
        }
        albumRepository.deleteById(trackingId);
        return ResponseEntity.noContent().build(); // Renvoie un code 204 (No Content)
    }
}