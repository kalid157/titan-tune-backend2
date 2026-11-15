package com.titan.tune.infrastructure.controller;

import com.titan.tune.application.dto.album.AlbumDTO;
import com.titan.tune.application.dto.album.UpdateAlbumAccessRequest;
import com.titan.tune.domain.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping("/all")
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @PutMapping("/{albumId}/access")
    public ResponseEntity<AlbumDTO> updateAccess(
            @PathVariable String albumId,
            @RequestBody UpdateAlbumAccessRequest request) {
        return ResponseEntity.ok(albumService.updateAccess(albumId, request));
    }
}