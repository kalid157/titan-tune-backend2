// CreateAlbumRequest.java
package com.titan.tune.application.dto.album;
import jakarta.validation.constraints.NotBlank;

public record CreateAlbumRequest(
    @NotBlank String titreAlbum,
    @NotBlank String artisteTrackingId,
    String imageAlbum
) {}