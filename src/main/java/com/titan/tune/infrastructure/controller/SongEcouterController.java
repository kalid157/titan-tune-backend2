package com.titan.tune.infrastructure.controller;

import com.titan.tune.domain.model.SongEcouter;
import com.titan.tune.domain.service.SongEcouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/songEcouter")
@RequiredArgsConstructor
public class SongEcouterController {
    private final SongEcouterService songEcouterService;

    @PostMapping("/register/{trackingIdUser}/{trackingIdSong}")
    public ResponseEntity<Void> registerListen(
            @PathVariable String trackingIdUser,
            @PathVariable String trackingIdSong) {
        songEcouterService.registerListen(trackingIdUser, trackingIdSong);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<SongEcouter>> searchHistory(@PathVariable String title) {
        return ResponseEntity.ok(songEcouterService.searchByTitle(title));
    }
}