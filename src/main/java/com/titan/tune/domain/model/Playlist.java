package com.titan.tune.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Playlist {
    @Id
    @Column(name = "tracking_id")
    private String trackingId;
    
    private String titre;
    private String imageUrl;
    private String clientTrackingId;
    private LocalDateTime createdAt;

    //  Permet de stocker une liste d'IDs de chansons dans la table playlist_songs
    @ElementCollection
    @CollectionTable(name = "playlist_songs", joinColumns = @JoinColumn(name = "playlist_id"))
    @Column(name = "song_tracking_id")
    @Builder.Default //  Important avec Lombok pour éviter le NullPointerException
    private List<String> songIds = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}