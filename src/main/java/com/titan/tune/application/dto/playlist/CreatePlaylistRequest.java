package com.titan.tune.application.dto.playlist;

import jakarta.validation.constraints.NotBlank;

public record CreatePlaylistRequest(
    @NotBlank String titre,
    String imageUrl,    // Optionnel
    Boolean visibilite  //  Accepté pour éviter les erreurs 400
) {}