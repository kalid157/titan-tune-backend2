package com.titan.tune.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "albums")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Album {
    @Id
    @Column(name = "tracking_id")
    private String trackingId;
    private String titreAlbum;
    private String nomArtiste;
    
    //  NOUVEAU CHAMP AJOUTÉ ICI
    private String artisteTrackingId; 

    private String imageAlbum;
    private Boolean isFree;
    private Boolean isVip;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (isFree == null) isFree = false;
        if (isVip == null) isVip = false;
    }
}