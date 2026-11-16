package com.titan.tune.infrastructure.controller;

import com.titan.tune.domain.service.SuivreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/suivre")
@RequiredArgsConstructor
public class SuivreController {
    private final SuivreService suivreService;

    @PostMapping("/follow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<Void> follow(
            @PathVariable String trackingIdFollower,
            @PathVariable String trackingIdArtiste) {
        suivreService.follow(trackingIdFollower, trackingIdArtiste);
        return ResponseEntity.ok().build();
    }

    // MODIFIÉ : Swagger demande un PUT pour unfollow
    @PutMapping("/unfollow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<Void> unfollow(
            @PathVariable String trackingIdFollower,
            @PathVariable String trackingIdArtiste) {
        suivreService.unfollow(trackingIdFollower, trackingIdArtiste);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unfollow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<Void> unfollow(
            @PathVariable String trackingIdFollower,
            @PathVariable String trackingIdArtiste) {
        suivreService.unfollow(trackingIdFollower, trackingIdArtiste);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/{trackingIdArtiste}")
    public ResponseEntity<List<String>> getFollowers(@PathVariable String trackingIdArtiste) {
        return ResponseEntity.ok(suivreService.getFollowers(trackingIdArtiste));
    }

    @GetMapping("/followings/{trackingIdClient}")
    public ResponseEntity<List<String>> getFollowings(@PathVariable String trackingIdClient) {
        return ResponseEntity.ok(suivreService.getFollowings(trackingIdClient));
    }

    //  NOUVEL ENDPOINT : Nombre de followers d'un artiste
    @GetMapping("/nbrFollowers/{trackingIdArtiste}")
    public ResponseEntity<Long> getFollowersCount(@PathVariable String trackingIdArtiste) {
        return ResponseEntity.ok(suivreService.getFollowersCount(trackingIdArtiste));
    }

    //  NOUVEL ENDPOINT : Nombre d'artistes suivis par un client
    @GetMapping("/nbrFollowings/{trackingIdClient}")
    public ResponseEntity<Long> getFollowingsCount(@PathVariable String trackingIdClient) {
        return ResponseEntity.ok(suivreService.getFollowingsCount(trackingIdClient));
    }
}