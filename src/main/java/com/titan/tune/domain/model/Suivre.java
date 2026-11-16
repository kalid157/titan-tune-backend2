package com.titan.tune.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "suivres")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Suivre {
    @Id
    private String trackingId;
    private String trackingIdFollower;
    private String trackingIdFollowing;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}