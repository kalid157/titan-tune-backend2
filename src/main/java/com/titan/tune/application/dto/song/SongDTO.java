package com.titan.tune.application.dto.song;

public record SongDTO(
    String trackingId,
    String titre,
    String audio,
    String artiste,
    String albumTrackingId,
    String artisteTrackingId,
    Integer durationSeconds,
    Boolean isVip
) {}