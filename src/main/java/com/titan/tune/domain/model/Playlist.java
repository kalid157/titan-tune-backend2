package com.titan.tune.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore; 
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder //  Lombok annotations for boilerplate code
public class Playlist {
    @Id
    @Column(name = "tracking_id")
    private String trackingId;
    
    private String titre;
    private String imageUrl;
    private String clientTrackingId;
    private LocalDateTime createdAt;

    @JsonIgnore 
    @ElementCollection
    @CollectionTable(name = "playlist_songs", joinColumns = @JoinColumn(name = "playlist_id"))
    @Column(name = "song_tracking_id")
    @Builder.Default 
    private List<String> songIds = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}