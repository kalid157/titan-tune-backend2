package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.album.AlbumDTO;
import com.titan.tune.application.dto.album.CreateAlbumRequest;
import com.titan.tune.domain.model.Album;
import com.titan.tune.domain.repository.AlbumRepository;
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

    @PostMapping("/create")
    public ResponseEntity<Album> create(@Valid @RequestBody CreateAlbumRequest request) {
        Album album = Album.builder()
                .trackingId(UUID.randomUUID().toString())
                .titreAlbum(request.titreAlbum())
                .artisteTrackingId(request.artisteTrackingId())
                .imageAlbum(request.imageAlbum())
                .build();
        return ResponseEntity.ok(albumRepository.save(album));
    }

    @PostMapping("/upload")
    public ResponseEntity<Album> upload(
            @RequestParam("titreAlbum") String titreAlbum,
            @RequestParam("artisteTrackingId") String artisteTrackingId,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = minioService.uploadFile(image);
        }

        Album album = Album.builder()
                .trackingId(UUID.randomUUID().toString())
                .titreAlbum(titreAlbum)
                .artisteTrackingId(artisteTrackingId)
                .imageAlbum(imageUrl)
                .build();
        return ResponseEntity.ok(albumRepository.save(album));
    }

    @PutMapping("/updateImage/{trackingId}")
    public ResponseEntity<Album> updateImage(
            @PathVariable String trackingId,
            @RequestParam("image") MultipartFile image) {
        Album album = albumRepository.findById(trackingId).orElseThrow();
        String imageUrl = minioService.uploadFile(image);
        album.setImageAlbum(imageUrl);
        return ResponseEntity.ok(albumRepository.save(album));
    }

    @GetMapping("/get/{trackingId}")
    public ResponseEntity<Album> getById(@PathVariable String trackingId) {
         Album album = albumRepository.findById(trackingId)
                .orElseThrow(() -> new com.titan.tune.common.exception.ResourceNotFoundException("Album introuvable avec l'ID : " + trackingId));
        return ResponseEntity.ok(album);
    }

    @DeleteMapping("/delete/{trackingId}")
    public ResponseEntity<Void> delete(@PathVariable String trackingId) {
        // Ici, tu devrais idéalement supprimer l'image de MinIO aussi
        albumRepository.deleteById(trackingId);
        return ResponseEntity.noContent().build();
    }
}