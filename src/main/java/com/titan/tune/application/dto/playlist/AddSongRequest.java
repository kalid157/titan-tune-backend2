package com.titan.tune.application.dto.playlist;
import jakarta.validation.constraints.NotBlank;

public record AddSongRequest(
    @NotBlank String playlistId,
    @NotBlank String songId
) {}