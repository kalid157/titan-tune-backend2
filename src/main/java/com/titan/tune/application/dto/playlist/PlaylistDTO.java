package com.titan.tune.application.dto.playlist;
import java.util.List;

public record PlaylistDTO(
    String trackingId,
    String titre,
    String imageUrl,
    String clientTrackingId,
    List<String> songIds
) {}