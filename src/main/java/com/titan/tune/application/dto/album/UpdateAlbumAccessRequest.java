package com.titan.tune.application.dto.album;

public record UpdateAlbumAccessRequest(
    Boolean isFree,
    Boolean isVip
) {}