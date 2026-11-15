package com.titan.tune.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"client_tracking_id", "song_tracking_id"})
})
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Favorite {
    @Id
    @Column(name = "tracking_id")
    private String trackingId;
    private String clientTrackingId;
    private String songTrackingId;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}