package com.titan.tune.application.dto.auth;

public record AuthResponse(
    String token,
    String trackingId,
    String email,
    String firstName,
    String lastName,
    Boolean isPremium
) {}