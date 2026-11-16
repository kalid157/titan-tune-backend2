package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.album.CreateAlbumRequest;
import com.titan.tune.common.exception.ResourceNotFoundException;
import com.titan.tune.domain.model.Album;
import com.titan.tune.domain.model.User;
import com.titan.tune.domain.repository.AlbumRepository;
import com.titan.tune.domain.repository.UserRepository; // Import ajouté
import com.titan.tune.domain.service.MinioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumRepository albumRepository;
    private final MinioService minioService;
    private final UserRepository userRepository; // Injection ajoutée

    @PostMapping("/create")
    public ResponseEntity<Album> create(@Valid @RequestBody CreateAlbumRequest request) {
        //  1. On cherche l'artiste grâce à l'ID fourni
        User artist = userRepository.findById(request.artisteTrackingId())
                .orElseThrow(() -> new ResourceNotFoundException("Artiste introuvable avec l'ID : " + request.artisteTrackingId()));

        // 2. On détermine son nom (on prend l'alias s'il existe, sinon Prénom + Nom)
        String artistName = (artist.getAlias() != null && !artist.getAlias().isEmpty()) 
                ? artist.getAlias() 
                : artist.getFirstName() + " " + artist.getLastName();

        // 3. On crée l'album avec TOUS les champs obligatoires
        Album album = Album.builder()
                .trackingId(UUID.randomUUID().toString())
                .titreAlbum(request.titreAlbum())
                .artisteTrackingId(request.artisteTrackingId())
                .nomArtiste(artistName) //  Le champ manquant est maintenant rempli !
                .imageAlbum(request.imageAlbum())
                .build();
                
        return ResponseEntity.ok(albumRepository.save(album));
    }

    @PostMapping("/upload")
    public ResponseEntity<Album> upload(
            @RequestParam("titreAlbum") String titreAlbum,
            @RequestParam("artisteTrackingId") String artisteTrackingId,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        // Même logique pour l'upload
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
                .nomArtiste(artistName) //  Rempli ici aussi
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

    @GetMapping("/get/{trackingId}")
    public ResponseEntity<Album> getById(@PathVariable String trackingId) {
        Album album = albumRepository.findById(trackingId)
                .orElseThrow(() -> new ResourceNotFoundException("Album introuvable avec l'ID : " + trackingId));
        return ResponseEntity.ok(album);
    }

    @DeleteMapping("/delete/{trackingId}")
    public ResponseEntity<Void> delete(@PathVariable String trackingId) {
        albumRepository.deleteById(trackingId);
        return ResponseEntity.noContent().build();
    }
}