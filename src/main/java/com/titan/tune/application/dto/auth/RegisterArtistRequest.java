package com.titan.tune.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterArtistRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String alias,
    String phone,
    @Email @NotBlank String email,
    @NotBlank @Size(min = 6) String password,
    String description
) {}