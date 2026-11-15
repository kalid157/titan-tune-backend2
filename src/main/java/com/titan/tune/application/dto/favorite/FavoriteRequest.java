package com.titan.tune.application.dto.favorite;
import jakarta.validation.constraints.NotBlank;

public record FavoriteRequest(
    @NotBlank String clientId,
    @NotBlank String songId
) {}