package com.titan.tune.infrastructure.controller;

import com.titan.tune.domain.model.Suivre;
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

    //  On change Void en Suivre
    @PostMapping("/follow/{trackingIdFollower}/{trackingIdArtiste}")
    public ResponseEntity<Suivre> follow(
            @PathVariable String trackingIdFollower,
            @PathVariable String trackingIdArtiste) {
        //  On renvoie directement l'objet retourné par le service
        Suivre suivre = suivreService.follow(trackingIdFollower, trackingIdArtiste);
        return ResponseEntity.ok(suivre);
    }

    // Un seul unfollow en PUT (reste en Void car delete ne renvoie rien)
    @PutMapping("/unfollow/{trackingIdFollower}/{trackingIdArtiste}")
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

    @GetMapping("/nbrFollowers/{trackingIdArtiste}")
    public ResponseEntity<Long> getFollowersCount(@PathVariable String trackingIdArtiste) {
        return ResponseEntity.ok(suivreService.getFollowersCount(trackingIdArtiste));
    }

    @GetMapping("/nbrFollowings/{trackingIdClient}")
    public ResponseEntity<Long> getFollowingsCount(@PathVariable String trackingIdClient) {
        return ResponseEntity.ok(suivreService.getFollowingsCount(trackingIdClient));
    }
}