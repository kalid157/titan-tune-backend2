package com.titan.tune.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "song_ecouter")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SongEcouter {
    @Id
    private String trackingId;
    private String userTrackingId;
    private String songTrackingId;
    private LocalDateTime listenedAt;

    @PrePersist
    public void prePersist() {
        if (listenedAt == null) listenedAt = LocalDateTime.now();
    }
}