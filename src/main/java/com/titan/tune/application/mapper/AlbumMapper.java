package com.titan.tune.application.mapper;

import com.titan.tune.application.dto.album.AlbumDTO;
import com.titan.tune.domain.model.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {
    public AlbumDTO toDto(Album album) {
        return new AlbumDTO(
            album.getTrackingId(), album.getTitreAlbum(), album.getNomArtiste(),
            album.getImageAlbum(), album.getIsFree(), album.getIsVip()
        );
    }
}