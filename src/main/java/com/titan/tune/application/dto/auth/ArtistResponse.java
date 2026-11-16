package com.titan.tune.application.dto.auth;

public record ArtistResponse(
    String trackingId,
    String firstName,
    String lastName,
    String alias,
    String email,
    String description
) {}