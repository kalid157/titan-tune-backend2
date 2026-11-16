package com.titan.tune.application.dto.song;
import jakarta.validation.constraints.NotBlank;

public record CreateSongRequest(
    @NotBlank String titre,
    @NotBlank String audio,
    @NotBlank String albumTrackingId,
    String categorieTrackingId
) {}