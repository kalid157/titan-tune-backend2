package com.titan.tune.application.dto.auth;

public record ClientResponse(
    String trackingId,
    String firstName,
    String lastName,
    String email,
    String phone
) {}