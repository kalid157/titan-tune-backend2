package com.titan.tune.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "songs")
@Data @NoArgsConstructor @AllArgsConstructor @Builder //  @Builder est indispensable ici
public class Song {
    @Id
    @Column(name = "tracking_id")
    private String trackingId;
    
    private String titre;
    private String audio;
    private String artiste;
    private String artisteTrackingId;
    private String albumTrackingId;
    private String categorieTrackingId;
    private Integer durationSeconds;
    private Boolean isVip;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (isVip == null) isVip = false;
    }
}