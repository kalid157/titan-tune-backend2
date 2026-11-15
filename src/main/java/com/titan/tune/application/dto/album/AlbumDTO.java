package com.titan.tune.application.dto.album;

public record AlbumDTO(
    String trackingId,
    String titreAlbum,
    String nomArtiste,
    String imageAlbum,
    Boolean isFree,
    Boolean isVip
) {}